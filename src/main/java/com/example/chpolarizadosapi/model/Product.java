package main.java.com.example.chpolarizadosapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private Long id;
    private String name;
    private String category;
    private LocalDateTime createdAt;
    private BigDecimal price;

    public Product() {}

    public Product(Long id, String name, String category, LocalDateTime createdAt, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.createdAt = createdAt;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
