package main.java.com.example.chpolarizadosapi.repository;

import main.java.com.example.chpolarizadosapi.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbc;

    public ProductRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private Product mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Product p = new Product();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));
        p.setCategory(rs.getString("category"));
        java.sql.Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) p.setCreatedAt(ts.toLocalDateTime());
        p.setPrice(rs.getBigDecimal("price"));
        return p;
    }

    public List<Product> findAll() {
        String sql = "SELECT id, name, category, created_at, price FROM products ORDER BY id";
        return jdbc.query(sql, this::mapRow);
    }

    public Optional<Product> findById(Long id) {
        String sql = "SELECT id, name, category, created_at, price FROM products WHERE id = ?";
        List<Product> list = jdbc.query(sql, this::mapRow, id);
        return list.stream().findFirst();
    }

    public Product save(Product product) {
        String sql = "INSERT INTO products(name, category, created_at, price) VALUES (?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            LocalDateTime created = product.getCreatedAt() != null ? product.getCreatedAt() : LocalDateTime.now();
            ps.setObject(3, created);
            ps.setBigDecimal(4, product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO);
            return ps;
        }, holder);
        Number key = holder.getKey();
        if (key != null) product.setId(key.longValue());
        return product;
    }

    public int update(Long id, Product product) {
        String sql = "UPDATE products SET name = ?, category = ?, price = ? WHERE id = ?";
        return jdbc.update(sql, product.getName(), product.getCategory(), product.getPrice(), id);
    }

    public int delete(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        return jdbc.update(sql, id);
    }
}
