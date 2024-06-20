// src/main/java/se/inyat/marketplace/controller/UserController.java
package se.inyat.marketplace.controller;

import se.inyat.marketplace.model.dto.UserForm;
import se.inyat.marketplace.model.dto.UserView;
import se.inyat.marketplace.model.entity.User;
import se.inyat.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<UserView> authenticateUser(@RequestBody UserForm userForm) {
        Optional<User> optionalUser = userService.findByEmail(userForm.getEmail());
        if (optionalUser.isPresent() && userService.checkPassword(userForm.getPassword(), optionalUser.get().getPassword())) {
            return ResponseEntity.ok(userService.convertToView(optionalUser.get()));
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserView>> getAllUsers() {
        List<UserView> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
}
