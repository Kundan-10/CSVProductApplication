# CSV Product Management Application

A Spring Boot application to manage products using CSV files. Supports uploading CSVs to add products and provides APIs to list, update, and delete products.

## ✅ Features

- Upload CSV to add multiple products
- Get paginated list of products
- Update product by ID
- Delete product by ID
- Swagger UI for testing
- CSV validation with error handling

---

## 📁 Sample CSV Format
 - id,name,description,price,quantity
 - ,Keyboard,Mechanical Keyboard,50.0,100
 - ,Mouse,Wireless Mouse,25.0,150

> **Note**: `id` should be blank when adding new products. Update and delete are handled via APIs.

---

## ⚙️ Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Swagger (springdoc-openapi)
- Apache Commons CSV
- MySQL

## 📦 Product Management API Endpoints

### 1. Upload CSV File
- **Endpoint:** `POST /api/products/upload-csv`
- **Description:** Upload a CSV file containing product data.

### 2. Get All Products (Paginated)
- **Endpoint:** `GET /api/products`
- **Query Params:**
  - `page` (default: `0`)
  - `size` (default: `10`)
- **Description:** Retrieve a paginated list of all products.

### 3. Update Product by ID
- **Endpoint:** `POST /api/products/update?id={id}`
- **Request Body:** JSON object of the updated product fields
- **Description:** Update an existing product by its ID.

### 4. Delete Product by ID
- **Endpoint:** `DELETE /api/products/{id}`
- **Description:** Delete a product by its ID.
