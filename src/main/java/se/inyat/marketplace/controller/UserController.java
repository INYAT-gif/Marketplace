// src/main/java/se/inyat/marketplace/controller/UserController.java
package se.inyat.marketplace.controller;

import se.inyat.marketplace.model.Advertisement;
import se.inyat.marketplace.model.User;
import se.inyat.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticateUser(@RequestParam String email, @RequestParam String password) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
