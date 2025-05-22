package com.vena.ecom.controller;

import com.vena.ecom.dto.AddProductCatalogRequest;
import com.vena.ecom.model.ProductCatalog;
import com.vena.ecom.service.impl.ProductCatalogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admin/catalog")
public class ProductCatalogController {

    @Autowired
    private ProductCatalogServiceImpl productCatalogService;

    @GetMapping
    public ResponseEntity<?> getAllProductCatalogs() {
        List<ProductCatalog> productCatalogList = productCatalogService.getAllProductsCatalogs();
        return new ResponseEntity<>(productCatalogList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductCatalogById(@PathVariable String id) {
        ProductCatalog productCatalog = productCatalogService.getproductCatalogById(id);
        return ResponseEntity.ok(productCatalog);
    }

    @PostMapping
    public ResponseEntity<?> createProductCatalog(@RequestBody AddProductCatalogRequest addProductCatalogRequest) {
        ProductCatalog createdCatalog = productCatalogService.createCatalogProduct(addProductCatalogRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCatalog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductCatalog(@PathVariable String id, @RequestBody ProductCatalog productCatalog) {
        ProductCatalog updatedCatalog = productCatalogService.updateProductCatalogById(id, productCatalog);
        return ResponseEntity.ok(updatedCatalog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductCatalog(@PathVariable String id) {
        productCatalogService.deleteProductCatalog(id);
        return ResponseEntity.ok("Product catalog deleted successfully.");
    }
}
