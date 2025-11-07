package com.example.chpolarizadosapi.controller;

import com.example.chpolarizadosapi.model.User;
import com.example.chpolarizadosapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository repository;
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody User user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo electrónico y la contraseña son obligatorios");
        }
        String email = user.getEmail().trim();
        String rawPassword = user.getPassword();
        if (!emailPattern.matcher(email).matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo electrónico no es válido");
        }
        if (rawPassword.length() < 6) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La contraseña debe tener al menos 6 caracteres");
        }
        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        user.setPassword(hashed);
        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        if (email == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email o la contraseña no pueden ser nulos");
        }
        Optional<User> opt = repository.findByEmail(email.trim());
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }
        User u = opt.get();
        String hashed = u.getPassword();
        if (BCrypt.checkpw(password, hashed)) {
            return ResponseEntity.ok("login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }
    }

    @GetMapping
    public List<User> list() {
        return repository.findAll();
    }
}
