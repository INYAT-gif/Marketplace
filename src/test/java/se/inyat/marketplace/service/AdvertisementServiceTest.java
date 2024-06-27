package se.inyat.marketplace.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.inyat.marketplace.model.entity.Advertisement;
import se.inyat.marketplace.model.entity.User;
import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.repository.AdvertisementRepository;

public class AdvertisementServiceTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdvertisementService advertisementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindActiveAdvertisements() {
        Advertisement ad1 = new Advertisement();
        ad1.setTitle("Ad 1");
        ad1.setExpirationDate(LocalDate.now().plusDays(5));

        Advertisement ad2 = new Advertisement();
        ad2.setTitle("Ad 2");
        ad2.setExpirationDate(LocalDate.now().plusDays(10));

        when(advertisementRepository.findByExpirationDateAfter(any(LocalDate.class))).thenReturn(List.of(ad1, ad2));

        List<AdvertisementView> activeAds = advertisementService.findActiveAdvertisements();
        assertEquals(2, activeAds.size());
        assertEquals("Ad 1", activeAds.get(0).getTitle());
        assertEquals("Ad 2", activeAds.get(1).getTitle());
    }

    @Test
    public void testSaveAdvertisement() {
        Advertisement ad = new Advertisement();
        ad.setTitle("New Ad");

        when(advertisementRepository.save(ad)).thenReturn(ad);

        Advertisement savedAd = advertisementService.saveAdvertisement(ad);
        assertEquals("New Ad", savedAd.getTitle());
        verify(advertisementRepository, times(1)).save(ad);
    }
}
