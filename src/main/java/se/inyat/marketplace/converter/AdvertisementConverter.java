// src/main/java/se/inyat/marketplace/converter/AdvertisementConverter.java
package se.inyat.marketplace.converter;

import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.model.entity.Advertisement;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementConverter {
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
