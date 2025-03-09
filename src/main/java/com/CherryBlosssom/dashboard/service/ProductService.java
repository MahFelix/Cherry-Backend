package com.CherryBlosssom.dashboard.service;

import com.CherryBlosssom.dashboard.model.Product;
import com.CherryBlosssom.dashboard.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Value("${app.upload-dir}")
    private String uploadDir;

    private Path rootLocation;

    public ProductService() {
        // Inicialização do rootLocation será feita no método init()
    }

    @PostConstruct
    public void init() {
        this.rootLocation = Paths.get(uploadDir);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar o diretório de uploads!", e);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image);
            product.setImage(imageUrl);
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails, MultipartFile image) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setSubtitle(productDetails.getSubtitle());
            product.setPrice(productDetails.getPrice());

            if (image != null && !image.isEmpty()) {
                String imageUrl = saveImage(image);
                product.setImage(imageUrl);
            }

            return productRepository.save(product);
        } else {
            throw new RuntimeException("Produto não encontrado com o ID: " + id);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private String saveImage(MultipartFile image) {
        try {
            if (image.isEmpty()) {
                throw new RuntimeException("Falha ao armazenar arquivo vazio.");
            }
            String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Files.copy(image.getInputStream(), this.rootLocation.resolve(filename));
            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao armazenar o arquivo.", e);
        }
    }
}