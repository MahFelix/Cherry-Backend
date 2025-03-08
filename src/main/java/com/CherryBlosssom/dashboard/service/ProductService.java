package com.CherryBlosssom.dashboard.service;

import com.CherryBlosssom.dashboard.model.Product;
import com.CherryBlosssom.dashboard.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Obter todos os produtos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Adicionar um novo produto
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Atualizar um produto existente
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setSubtitle(productDetails.getSubtitle());
            product.setImage(productDetails.getImage());
            product.setHoverImage(productDetails.getHoverImage());
            product.setRating(productDetails.getRating());
            product.setPrice(productDetails.getPrice());
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Produto não encontrado com o ID: " + id);
        }
    }

    // Excluir um produto
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}