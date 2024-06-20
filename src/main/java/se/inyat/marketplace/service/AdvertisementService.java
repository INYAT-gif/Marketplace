// src/main/java/se/inyat/marketplace/service/AdvertisementService.java
package se.inyat.marketplace.service;

import se.inyat.marketplace.model.Advertisement;
import se.inyat.marketplace.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdvertisementService {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Advertisement saveAdvertisement(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    public List<Advertisement> findActiveAdvertisements() {
        return advertisementRepository.findByExpirationDateAfter(LocalDate.now());
    }
}
