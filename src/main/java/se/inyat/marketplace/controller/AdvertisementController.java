// src/main/java/se/inyat/marketplace/controller/AdvertisementController.java
package se.inyat.marketplace.controller;

import se.inyat.marketplace.model.Advertisement;
import se.inyat.marketplace.model.User;
import se.inyat.marketplace.service.AdvertisementService;
import se.inyat.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Advertisement> createAdvertisement(@RequestBody @Valid Advertisement advertisement, @RequestParam String email, @RequestParam String username, @RequestParam String password) {
        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                advertisement.setUser(user);
                return ResponseEntity.ok(advertisementService.saveAdvertisement(advertisement));
            } else {
                return ResponseEntity.status(401).build();
            }
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(username);
            newUser.setPassword(password);
            userService.saveUser(newUser);
            advertisement.setUser(newUser);
            return ResponseEntity.ok(advertisementService.saveAdvertisement(advertisement));
        }
    }

    @GetMapping
    public ResponseEntity<List<Advertisement>> getActiveAdvertisements() {
        return ResponseEntity.ok(advertisementService.findActiveAdvertisements());
    }
}

