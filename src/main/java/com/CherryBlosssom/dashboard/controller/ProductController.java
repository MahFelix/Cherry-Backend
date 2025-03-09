package com.CherryBlosssom.dashboard.controller;

import com.CherryBlosssom.dashboard.model.Product;
import com.CherryBlosssom.dashboard.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "https://cherryblossomsite.netlify.app/")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Obter todos os produtos
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Adicionar um novo produto
    @PostMapping
    public Product addProduct(@RequestParam("name") String name,
                              @RequestParam("subtitle") String subtitle,
                              @RequestParam("price") double price,
                              @RequestParam("image") MultipartFile image) {
        Product product = new Product();
        product.setName(name);
        product.setSubtitle(subtitle);
        product.setPrice(price);
        return productService.addProduct(product, image);
    }

    // Atualizar um produto existente
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestParam("name") String name,
                                 @RequestParam("subtitle") String subtitle,
                                 @RequestParam("price") double price,
                                 @RequestParam(value = "image", required = false) MultipartFile image) {
        Product productDetails = new Product();
        productDetails.setName(name);
        productDetails.setSubtitle(subtitle);
        productDetails.setPrice(price);
        return productService.updateProduct(id, productDetails, image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        // Lógica para excluir o produto
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}