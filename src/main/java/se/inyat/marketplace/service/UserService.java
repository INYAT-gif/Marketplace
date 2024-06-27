package se.inyat.marketplace.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.inyat.marketplace.converter.UserConverter;
import se.inyat.marketplace.model.dto.UserForm;
import se.inyat.marketplace.model.dto.UserView;
import se.inyat.marketplace.model.entity.User;
import se.inyat.marketplace.repository.UserRepository;
import se.inyat.marketplace.util.CustomPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    /**
     * Finds a user by email.
     *
     * @param email the email of the user
     * @return an optional containing the user if found, or empty if not found
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Saves a user to the database.
     *
     * @param user the user to save
     * @return the saved user
     */
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Finds all users.
     *
     * @return a list of user views
     */
    public List<UserView> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userConverter::convertToView).collect(Collectors.toList());
    }

    /**
     * Converts a User entity to a UserView DTO.
     *
     * @param user the user entity
     * @return the user view
     */
    public UserView convertToView(User user) {
        return userConverter.convertToView(user);
    }

    /**
     * Checks if the raw password matches the encoded password.
     *
     * @param rawPassword the raw password
     * @param encodedPassword the encoded password
     * @return true if the passwords match, false otherwise
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Authenticates a user by email and password.
     *
     * @param userForm the form containing user login data
     * @return the authenticated user view
     */
    public ResponseEntity<UserView> authenticateUser(@Valid UserForm userForm) {
        Optional<User> optionalUser = findByEmail(userForm.getEmail());
        if (optionalUser.isPresent() && checkPassword(userForm.getPassword(), optionalUser.get().getPassword())) {
            return ResponseEntity.ok(convertToView(optionalUser.get()));
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
