package se.inyat.marketplace.service;

import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.model.entity.Advertisement;
import se.inyat.marketplace.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    @Autowired
    private AdvertisementRepository advertisementRepository;

    public Advertisement saveAdvertisement(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    public List<AdvertisementView> findActiveAdvertisements() {
        List<Advertisement> advertisements = advertisementRepository.findByExpirationDateAfter(LocalDate.now());
        return advertisements.stream().map(this::convertToView).collect(Collectors.toList());
    }

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
