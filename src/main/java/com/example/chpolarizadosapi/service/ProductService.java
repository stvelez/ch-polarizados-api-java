package main.java.com.example.chpolarizadosapi.service;

import main.java.com.example.chpolarizadosapi.model.Product;
import main.java.com.example.chpolarizadosapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> listAll() {
        return repository.findAll();
    }

    public Optional<Product> getById(Long id) {
        return repository.findById(id);
    }

    public Product create(Product product) {
        return repository.save(product);
    }

    public boolean update(Long id, Product product) {
        return repository.update(id, product) > 0;
    }

    public boolean delete(Long id) {
        return repository.delete(id) > 0;
    }
}
