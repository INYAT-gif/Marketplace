package se.inyat.marketplace.controller;

import se.inyat.marketplace.model.dto.AdvertisementForm;
import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for managing advertisements.
 */
@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    /**
     * Get all active advertisements.
     *
     * @return List of active advertisements
     */
    @GetMapping
    public ResponseEntity<List<AdvertisementView>> getActiveAdvertisements() {
        List<AdvertisementView> advertisements = advertisementService.findActiveAdvertisements();
        return ResponseEntity.ok(advertisements);
    }

    /**
     * Create a new advertisement.
     *
     * @param advertisementForm the form containing advertisement details
     * @return the created advertisement
     */
    @PostMapping
    public ResponseEntity<AdvertisementView> createAdvertisement(@Valid @RequestBody AdvertisementForm advertisementForm) {
        return advertisementService.createAdvertisement(advertisementForm);
    }
}
