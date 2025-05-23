package com.apeiros.CSVProductManager.service;

import com.apeiros.CSVProductManager.exception.ProductException;
import com.apeiros.CSVProductManager.models.Product;
import com.apeiros.CSVProductManager.repository.ProductRepo;
import com.apeiros.CSVProductManager.utility.CSVHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepo productRepo;

    @Override
    public void processCsv(MultipartFile file) throws ProductException, IOException {
        List<String[]> records = CSVHelper.readCsv(file.getInputStream());

        for (int i = 1; i < records.size(); i++) {
            String[] row = records.get(i);

            try {
                String name = row.length > 0 ? row[0].trim() : null;
                String desc = row.length > 1 ? row[1].trim() : null;
                Double price = tryParseDouble(row);
                Integer quantity = tryParseInt(row);

                if (name == null || name.isEmpty()) {
                    throw new IllegalArgumentException("Product name is required at row " + (i + 1));
                }

                Product product = new Product(null, name, desc, price, quantity);
                productRepo.save(product);

            } catch (Exception e) {
                log.error("Failed to process row {}: {}", i + 1, Arrays.toString(row), e);
            }
        }

//            switch (operation.toUpperCase()) {
//                case "ADD":
//                    productRepo.save(new Product(null, name, desc, price, quantity));
//                    break;
//                case "EDIT":
//                    Product existing = productRepo.findById(id).orElse(null);
//                    if (existing != null) {
//                        existing.setName(name);
//                        existing.setDescription(desc);
//                        existing.setPrice(price);
//                        existing.setQuantity(quantity);
//                        productRepo.save(existing);
//                    }
//                    break;
//                case "DELETE":
//                    productRepo.deleteById(id);
//                    break;
//            }
        }


    @Override
    public Page<Product> getProducts(Pageable pageable) {
        return productRepo.findAll((Pageable) pageable);
    }

    @Override
    public Product updateProduct(Integer id, Product updatedProduct) throws ProductException {
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new ProductException("Product with ID " + id + " not found"));

        if (updatedProduct.getName() != null && !updatedProduct.getName().isBlank()) {
            existing.setName(updatedProduct.getName());
        }

        if (updatedProduct.getDescription() != null && !updatedProduct.getDescription().isBlank()) {
            existing.setDescription(updatedProduct.getDescription());
        }

        if (updatedProduct.getPrice() != null) {
            existing.setPrice(updatedProduct.getPrice());
        }

        if (updatedProduct.getQuantity() != null) {
            existing.setQuantity(updatedProduct.getQuantity());
        }

        return productRepo.save(existing);

    }

    @Override
    public String deleteProduct(Integer id) throws ProductException {
        if (!productRepo.existsById(id)) {
            throw new ProductException("Product with ID " + id + " not found");
        }
        productRepo.deleteById(id);
        return "Product with ID " + id + " has been successfully deleted.";
    }

    private Integer tryParseInt(String[] row) {
        if (row.length > 3 && row[3] != null && !row[3].trim().isEmpty()) {
            try {
                return Integer.parseInt(row[3].trim());
            } catch (NumberFormatException e) {
                log.warn("Invalid integer '{}' at column {} in row {}", row[3], 3, Arrays.toString(row));
            }
        }
        return null;
    }

    private Double tryParseDouble(String[] row) {
        if (row.length > 2 && row[2] != null && !row[2].trim().isEmpty()) {
            try {
                return Double.parseDouble(row[2].trim());
            } catch (NumberFormatException e) {
                log.warn("Invalid double '{}' at column {} in row {}", row[2], 2, Arrays.toString(row));
            }
        }
        return null;
    }
}
