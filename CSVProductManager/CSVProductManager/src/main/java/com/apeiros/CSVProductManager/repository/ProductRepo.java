package com.apeiros.CSVProductManager.repository;

import com.apeiros.CSVProductManager.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
