package se.inyat.marketplace.controller;

import se.inyat.marketplace.model.dto.AdvertisementForm;
import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.model.entity.Advertisement;
import se.inyat.marketplace.model.entity.User;
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
    public ResponseEntity<AdvertisementView> createAdvertisement(@RequestBody @Valid AdvertisementForm advertisementForm) {
        Optional<User> optionalUser = userService.findByEmail(advertisementForm.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getUsername().equals(advertisementForm.getUsername()) && userService.checkPassword(advertisementForm.getPassword(), user.getPassword())) {
                Advertisement advertisement = new Advertisement();
                advertisement.setTitle(advertisementForm.getTitle());
                advertisement.setDescription(advertisementForm.getDescription());
                advertisement.setExpirationDate(advertisementForm.getExpirationDate());
                advertisement.setUser(user);
                return ResponseEntity.ok(advertisementService.convertToView(advertisementService.saveAdvertisement(advertisement)));
            } else {
                return ResponseEntity.status(401).build();
            }
        } else {
            User newUser = new User();
            newUser.setEmail(advertisementForm.getEmail());
            newUser.setUsername(advertisementForm.getUsername());
            newUser.setPassword(advertisementForm.getPassword());
            userService.saveUser(newUser);
            Advertisement advertisement = new Advertisement();
            advertisement.setTitle(advertisementForm.getTitle());
            advertisement.setDescription(advertisementForm.getDescription());
            advertisement.setExpirationDate(advertisementForm.getExpirationDate());
            advertisement.setUser(newUser);
            return ResponseEntity.ok(advertisementService.convertToView(advertisementService.saveAdvertisement(advertisement)));
        }
    }

    @GetMapping
    public ResponseEntity<List<AdvertisementView>> getActiveAdvertisements() {
        List<AdvertisementView> advertisements = advertisementService.findActiveAdvertisements();
        return ResponseEntity.ok(advertisements);
    }
}
