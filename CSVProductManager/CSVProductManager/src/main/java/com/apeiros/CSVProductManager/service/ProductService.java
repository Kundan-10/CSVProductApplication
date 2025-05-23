package com.apeiros.CSVProductManager.service;

import com.apeiros.CSVProductManager.exception.ProductException;
import com.apeiros.CSVProductManager.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    void processCsv(MultipartFile file) throws ProductException, IOException;
    Page<Product> getProducts(Pageable pageable);
    Product updateProduct(Integer id, Product product) throws ProductException;
    String deleteProduct(Integer id) throws ProductException;
}
