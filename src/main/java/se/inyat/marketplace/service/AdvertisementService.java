package se.inyat.marketplace.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.inyat.marketplace.model.dto.AdvertisementForm;
import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.model.entity.Advertisement;
import se.inyat.marketplace.model.entity.User;
import se.inyat.marketplace.repository.AdvertisementRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing advertisements.
 */
@Service
public class AdvertisementService {

    @Autowired
    private UserService userService;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    /**
     * Saves an advertisement to the database.
     *
     * @param advertisement the advertisement to save
     * @return the saved advertisement
     */
    public Advertisement saveAdvertisement(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    /**
     * Finds all active advertisements.
     *
     * @return a list of active advertisement views
     */
    public List<AdvertisementView> findActiveAdvertisements() {
        List<Advertisement> advertisements = advertisementRepository.findByExpirationDateAfter(LocalDate.now());
        return advertisements.stream().map(this::convertToView).collect(Collectors.toList());
    }

    /**
     * Converts an Advertisement entity to an AdvertisementView DTO.
     *
     * @param advertisement the advertisement entity
     * @return the advertisement view
     */
    public AdvertisementView convertToView(Advertisement advertisement) {
        AdvertisementView advertisementView = new AdvertisementView();
        advertisementView.setId(advertisement.getId());
        advertisementView.setTitle(advertisement.getTitle());
        advertisementView.setDescription(advertisement.getDescription());
        advertisementView.setExpirationDate(advertisement.getExpirationDate());
        advertisementView.setUser(null); // Avoid recursion
        return advertisementView;
    }

    /**
     * Creates a new advertisement. If the user does not exist, a new user is created.
     *
     * @param advertisementForm the form containing advertisement and user data
     * @return the created advertisement view
     */
    public ResponseEntity<AdvertisementView> createAdvertisement(@Valid AdvertisementForm advertisementForm) {
        Optional<User> optionalUser = userService.findByEmail(advertisementForm.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getUsername().equals(advertisementForm.getUsername()) && userService.checkPassword(advertisementForm.getPassword(), user.getPassword())) {
                Advertisement advertisement = new Advertisement();
                advertisement.setTitle(advertisementForm.getTitle());
                advertisement.setDescription(advertisementForm.getDescription());
                advertisement.setExpirationDate(advertisementForm.getExpirationDate());
                advertisement.setUser(user);
                return ResponseEntity.ok(convertToView(saveAdvertisement(advertisement)));
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
            return ResponseEntity.ok(convertToView(saveAdvertisement(advertisement)));
        }
    }
}
