package se.inyat.marketplace.controller;

import se.inyat.marketplace.model.dto.UserForm;
import se.inyat.marketplace.model.dto.UserView;
import se.inyat.marketplace.model.entity.User;
import se.inyat.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

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
     * Authenticates a user by email and password.
     *
     * @param userForm the form containing user login data
     * @return the authenticated user view
     */
    @PostMapping("/authenticate")
    public ResponseEntity<UserView> authenticateUser(@RequestBody @Valid UserForm userForm) {
        Optional<User> optionalUser = userService.findByEmail(userForm.getEmail());
        if (optionalUser.isPresent() && userService.checkPassword(userForm.getPassword(), optionalUser.get().getPassword())) {
            return ResponseEntity.ok(userService.convertToView(optionalUser.get()));
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Retrieves all users.
     *
     * @return a list of user views
     */
    @GetMapping
    public ResponseEntity<List<UserView>> getAllUsers() {
        List<UserView> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
}
