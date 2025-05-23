package com.apeiros.CSVProductManager.controller;

import com.apeiros.CSVProductManager.exception.ProductException;
import com.apeiros.CSVProductManager.models.Product;
import com.apeiros.CSVProductManager.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Endpoints for Managing the Products")
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/upload-csv")
    @Operation(summary = "upload New File", description = "upload New file.")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            productService.processCsv(file);
            return ResponseEntity.ok("CSV processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Get all Products", description = "Fetch all Products from the system.")
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getProducts(pageable);
        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    @PostMapping("/update")
    @Operation(summary = "Update Product", description = "Updates a product by its ID")
    public ResponseEntity<Product> updateProducts(
            @RequestParam ("id") Integer id,
            @RequestBody Product product) throws ProductException {

             Product product1 = productService.updateProduct(id, product);
             return new ResponseEntity<>(product1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Product by id", description = "Delete Product by id.")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) throws ProductException {
        String product1 = productService.deleteProduct(id);
        return new ResponseEntity<>(product1, HttpStatus.ACCEPTED);
    }

}
