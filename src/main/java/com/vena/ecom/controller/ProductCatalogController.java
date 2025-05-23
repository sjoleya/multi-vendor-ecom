package com.vena.ecom.controller;

import com.vena.ecom.dto.request.AddProductCatalogRequest;
import com.vena.ecom.dto.response.ProductCatalogResponse;
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
    public ResponseEntity<List<ProductCatalogResponse>> getAllProductCatalogs() {
        List<ProductCatalogResponse> productCatalogList = productCatalogService.getAllProductsCatalogs();
        return new ResponseEntity<>(productCatalogList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCatalogResponse> getProductCatalogById(@PathVariable String id) {
        ProductCatalogResponse productCatalog = productCatalogService.getproductCatalogById(id);
        return ResponseEntity.ok(productCatalog);
    }

    @PostMapping
    public ResponseEntity<ProductCatalogResponse> createProductCatalog(
            @RequestBody AddProductCatalogRequest addProductCatalogRequest) {
        ProductCatalogResponse createdCatalog = productCatalogService.createCatalogProduct(addProductCatalogRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCatalog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCatalogResponse> updateProductCatalog(@PathVariable String id,
            @RequestBody AddProductCatalogRequest addProductCatalogRequest) {
        ProductCatalogResponse updatedCatalog = productCatalogService.updateProductCatalogById(id,
                addProductCatalogRequest);
        return ResponseEntity.ok(updatedCatalog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductCatalog(@PathVariable String id) {
        productCatalogService.deleteProductCatalog(id);
        return ResponseEntity.ok("Product catalog deleted successfully.");
    }
}
