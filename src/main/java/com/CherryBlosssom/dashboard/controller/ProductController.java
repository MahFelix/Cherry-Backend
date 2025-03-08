package com.CherryBlosssom.dashboard.controller;

import com.CherryBlosssom.dashboard.model.Product;
import com.CherryBlosssom.dashboard.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping public List<Product> getAllProducts()
    { return productService.getAllProducts(); }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> addProduct(
            @RequestParam("name") String name,
            @RequestParam("subtitle") String subtitle,
            @RequestParam("price") double price,
            @RequestParam("image") MultipartFile file) throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setSubtitle(subtitle);
        product.setPrice(price);
        product.setImage(file.getBytes());

        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("subtitle") String subtitle,
            @RequestParam("price") double price,
            @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {

        byte[] imageBytes = file != null ? file.getBytes() : null;
        Product updatedProduct = productService.updateProduct(id, name, subtitle, price, imageBytes)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID: " + id));

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}