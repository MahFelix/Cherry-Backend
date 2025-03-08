package com.CherryBlosssom.dashboard.service;

import com.CherryBlosssom.dashboard.model.Product;
import com.CherryBlosssom.dashboard.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    // Obter todos os produtos
    public List<Product> getAllProducts() {
        logger.info("Buscando todos os produtos");
        return productRepository.findAll();
    }

    // Adicionar um novo produto
    public Product addProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode estar vazio.");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("O preço do produto não pode ser negativo.");
        }
        logger.info("Adicionando novo produto: {}", product.getName());
        return productRepository.save(product);
    }

    // Atualizar um produto existente
    public Optional<Product> updateProduct(Long id, String name, String subtitle, double price, byte[] imageBytes) {
        logger.info("Atualizando produto com ID: {}", id);
        return productRepository.findById(id).map(product -> {
            product.setName(name);
            product.setSubtitle(subtitle);
            product.setPrice(price);
            if (imageBytes != null) {
                product.setImage(imageBytes); // Armazena a imagem como byte[]
            }
            return productRepository.save(product);
        });
    }

    // Excluir um produto
    public boolean deleteProduct(Long id) {
        logger.info("Excluindo produto com ID: {}", id);
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}