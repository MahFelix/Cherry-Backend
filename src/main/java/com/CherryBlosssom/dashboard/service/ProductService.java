package com.CherryBlosssom.dashboard.service;

import com.CherryBlosssom.dashboard.model.Product;
import com.CherryBlosssom.dashboard.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    public Product updateProduct(Long id, String name, String subtitle, double price, byte[] imageBytes) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(name);
            product.setSubtitle(subtitle);
            product.setPrice(price);
            if (imageBytes != null) {
                product.setImage(Arrays.toString(imageBytes).getBytes()); // Convertendo para String
            }
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