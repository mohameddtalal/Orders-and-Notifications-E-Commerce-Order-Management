package com.example.demo.Product;

import com.example.demo.DataBase.IDB;
import com.example.demo.DataBase.MemoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProdcutService {

    @Autowired
    private final IDB database = MemoryDB.getInstance();

    public List<Product> getAllProducts() {
        return database.getProducts();
    }

    public void createProductInternal(String name, String vendor, String category, double price) {
        boolean flag= false;
        for(Product p : database.getProducts()){
            if(p.getName().equals(name)){
                p.setRemainingParts(p.getRemainingParts()+1);
                flag = true;
                break;
            }
        }
        if (!flag){
            String serialNo = generateProductId();
            Product product = new Product(serialNo, name, vendor, category, price);
            database.addProduct(product);
        }
    }
    public ResponseEntity<?> createProduct(Product productRequest) {
        createProductInternal(
                productRequest.getName(),
                productRequest.getVendor(),
                productRequest.getCategory(),
                productRequest.getPrice()
        );
        return ResponseEntity.ok("Product created successfully");
    }

    private Optional<Product> getProductByName(String productName) {
        return database.getProducts().stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst();
    }

    public String generateProductId() {
        int nextId = database.getProducts().size() + 1; // Assuming you have a list of products
        String formattedId = String.format("%05d", nextId); // Pad with leading zeros to 5 digits
        return formattedId;
    }

    public Product updateProduct(String serialNumber, Product product) {
        // Add validation for product data here
        Optional<Product> existingProduct = database.getProductById(serialNumber);
        if (existingProduct.isPresent()) {
            database.removeProduct(serialNumber);
            database.addProduct(product);
            return product;
        } else {
            throw new NoSuchElementException("Product not found");
        }
    }

    public Product getProduct(String name) {
        Optional<Product> product = database.findProductByName(name);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new NoSuchElementException(name);
        }
    }

    public void deleteProduct(String serialNumber) {
        database.removeProduct(serialNumber);
    }

}
