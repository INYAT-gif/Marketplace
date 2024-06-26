package se.inyat.marketplace.service;

import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.model.entity.Advertisement;
import se.inyat.marketplace.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing advertisements.
 */
@Service
public class AdvertisementService {

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
}
