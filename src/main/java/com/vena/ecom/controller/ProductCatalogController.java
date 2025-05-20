package com.vena.ecom.controller;

import com.vena.ecom.model.ProductCatalog;
import com.vena.ecom.service.impl.ProductCatalogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/admin/")
public class ProductCatalogController {

    @Autowired
    ProductCatalogServiceImpl productCatalogService;

    @GetMapping("/getallpc")
    public List<ProductCatalog> getAllCatalogProduct()
    {
        List<ProductCatalog> productCatalogList = productCatalogService.getAllProductsCatalogs();
        return productCatalogList;
    }
    @GetMapping("/getpc/{id}")
    public ResponseEntity<?> getProductCatalogById(@PathVariable String id)
    {
        ProductCatalog  productCatalog = productCatalogService.getproductCatalogById(id);
        return ResponseEntity.ok(productCatalog);
    }
    @PostMapping("/addpc")
    public ResponseEntity<?> createProductCatalog(@RequestBody ProductCatalog productCatalog)
    {
        ProductCatalog productCatalogAns = productCatalogService.createCatalogProduct(productCatalog);
        return  ResponseEntity.status(HttpStatus.CREATED).body(productCatalogAns);
    }
    @PutMapping("/updatepc/")
    public ResponseEntity<?> updateProductCatalogById(@PathVariable String id, @RequestBody ProductCatalog productCatalog)
    {
        ProductCatalog productCatalogAns = productCatalogService.updateProductCatalogById(id,productCatalog);
        return  ResponseEntity.ok(productCatalog);
    }
    @DeleteMapping("/deletepc/{id}")
    public ResponseEntity<String>  deleteProductCatalog(@PathVariable String id) {
        productCatalogService.deleteProductCatalog(id); // will throw StudentNotFoundException if not found
        return ResponseEntity.ok("Student deleted successfully.");
    }
}
