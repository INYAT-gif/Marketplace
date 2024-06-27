package se.inyat.marketplace.controller;

import se.inyat.marketplace.model.dto.UserForm;
import se.inyat.marketplace.model.dto.UserView;
import se.inyat.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users.
     *
     * @return List of user views
     */
    @GetMapping
    public ResponseEntity<List<UserView>> getAllUsers() {
        List<UserView> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Authenticate a user.
     *
     * @param userForm the form containing user login data
     * @return the authenticated user view
     */
    @PostMapping("/authenticate")
    public ResponseEntity<UserView> authenticateUser(@Valid @RequestBody UserForm userForm) {
        return userService.authenticateUser(userForm);
    }
}
