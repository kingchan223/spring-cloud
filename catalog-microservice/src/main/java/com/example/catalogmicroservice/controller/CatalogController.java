package com.example.catalogmicroservice.controller;

import com.example.catalogmicroservice.jpa.CatalogEntity;
import com.example.catalogmicroservice.service.CatalogService;
import com.example.catalogmicroservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/catalog-service/")
@RestController
public class CatalogController {
    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working Catalog-service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> catalogList() {
        Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();
        List<ResponseCatalog> result = new ArrayList<>();
        catalogList.forEach(u -> result.add(new ModelMapper().map(u, ResponseCatalog.class)));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
