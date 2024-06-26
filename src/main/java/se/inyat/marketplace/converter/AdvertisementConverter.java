package se.inyat.marketplace.converter;

import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.model.entity.Advertisement;
import org.springframework.stereotype.Component;

/**
 * Converter for Advertisement entities to DTOs.
 */
@Component
public class AdvertisementConverter {

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
