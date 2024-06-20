// src/main/java/se/inyat/marketplace/service/UserService.java
package se.inyat.marketplace.service;

import se.inyat.marketplace.converter.UserConverter;

import se.inyat.marketplace.model.dto.UserView;
import se.inyat.marketplace.model.entity.User;
import se.inyat.marketplace.repository.UserRepository;
import se.inyat.marketplace.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<UserView> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userConverter::convertToView).collect(Collectors.toList());
    }

    public UserView convertToView(User user) {
        return userConverter.convertToView(user);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
