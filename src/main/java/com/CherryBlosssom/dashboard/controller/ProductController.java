package com.CherryBlosssom.dashboard.controller;

import com.CherryBlosssom.dashboard.model.Product;
import com.CherryBlosssom.dashboard.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Adicionar um novo produto com imagem
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
        product.setImage(Arrays.toString(file.getBytes()).getBytes()); // Convertendo para byte[]

        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("subtitle") String subtitle,
            @RequestParam("price") double price,
            @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {

        Product product = productService.updateProduct(id, name, subtitle, price, file != null ? file.getBytes() : null);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}