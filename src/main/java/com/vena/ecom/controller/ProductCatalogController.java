package com.vena.ecom.controller;

import com.vena.ecom.dto.request.AddProductCatalogRequest;
import com.vena.ecom.dto.response.ProductCatalogResponse;
import com.vena.ecom.service.impl.ProductCatalogServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/catalog")
public class ProductCatalogController {

    private static final Logger logger = LoggerFactory.getLogger(ProductCatalogController.class);

    @Autowired
    private ProductCatalogServiceImpl productCatalogService;

    @GetMapping
    public ResponseEntity<List<ProductCatalogResponse>> getAllProductCatalogs() {
        logger.info("Received request to get all product catalogs");
        List<ProductCatalogResponse> productCatalogList = productCatalogService.getAllProductsCatalogs();
        if (productCatalogList.isEmpty()) {
            logger.warn("No product catalogs found in the system.");
        } else {
            logger.debug("Total products fetched: {}", productCatalogList.size());
        }
        return new ResponseEntity<>(productCatalogList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCatalogResponse> getProductCatalogById(@PathVariable String id) {
        logger.info("Fetching product catalog by ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            logger.warn("Empty or null ID received in request path");
        }
        ProductCatalogResponse productCatalog = productCatalogService.getproductCatalogById(id);
        return ResponseEntity.ok(productCatalog);
    }

    @PostMapping
    public ResponseEntity<ProductCatalogResponse> createProductCatalog(
            @RequestBody AddProductCatalogRequest addProductCatalogRequest) {
        logger.info("Request received to create new catalog: {}", addProductCatalogRequest.getName());
        if (addProductCatalogRequest.getCategoryId() == null || addProductCatalogRequest.getCategoryId().isEmpty()) {
            logger.warn("Catalog creation request received with empty categoryId");
        }
        ProductCatalogResponse createdCatalog = productCatalogService.createCatalogProduct(addProductCatalogRequest);
        logger.debug("Product catalog created with ID: {}", createdCatalog.id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCatalog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCatalogResponse> updateProductCatalog(@PathVariable String id,
                                                                       @RequestBody AddProductCatalogRequest addProductCatalogRequest) {
        logger.info("Request to update product catalog ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            logger.warn("Empty ID provided for update");
        }
        ProductCatalogResponse updatedCatalog = productCatalogService.updateProductCatalogById(id,
                addProductCatalogRequest);
        logger.debug("Updated catalog: {}", updatedCatalog.id);
        return ResponseEntity.ok(updatedCatalog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductCatalog(@PathVariable String id) {
        logger.info("Deleting product catalog with ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            logger.warn("Attempted to delete catalog with an empty ID");
        }
        productCatalogService.deleteProductCatalog(id);
        logger.info("Deleted product catalog successfully with ID: {}", id);
        return ResponseEntity.ok("Product catalog deleted successfully.");
    }
}
