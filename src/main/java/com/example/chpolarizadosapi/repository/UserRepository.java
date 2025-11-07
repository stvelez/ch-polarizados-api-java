package main.java.com.example.chpolarizadosapi.repository;

import main.java.com.example.chpolarizadosapi.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private User mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        return u;
    }

    public int create(User user) {
        String sql = "INSERT INTO users(email, password) VALUES (?, ?)";
        return jdbc.update(sql, user.getEmail(), user.getPassword());
    }

    public List<User> findAll() {
        String sql = "SELECT id, email, password FROM users ORDER BY id";
        return jdbc.query(sql, this::mapRow);
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, email, password FROM users WHERE email = ?";
        List<User> list = jdbc.query(sql, this::mapRow, email);
        return list.stream().findFirst();
    }
}
