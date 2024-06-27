package se.inyat.marketplace.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import se.inyat.marketplace.model.dto.AdvertisementForm;
import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.service.AdvertisementService;

@WebMvcTest(AdvertisementController.class)
public class AdvertisementControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvertisementService advertisementService;

    @Test
    public void testGetActiveAdvertisements() throws Exception {
        AdvertisementView adView = new AdvertisementView();
        adView.setTitle("Active Ad");

        given(advertisementService.findActiveAdvertisements()).willReturn(List.of(adView));

        mockMvc.perform(get("/api/advertisements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Active Ad"));
    }

    @Test
    public void testCreateAdvertisement() throws Exception {
        AdvertisementForm adForm = new AdvertisementForm();
        adForm.setTitle("New Ad");
        adForm.setDescription("Ad description");
        adForm.setExpirationDate(LocalDate.now().plusDays(10));
        adForm.setEmail("user@example.com");
        adForm.setUsername("sampleuser");
        adForm.setPassword("samplepassword");

        AdvertisementView adView = new AdvertisementView();
        adView.setTitle("New Ad");

        given(advertisementService.createAdvertisement(any(AdvertisementForm.class))).willReturn(ResponseEntity.ok(adView));

        mockMvc.perform(post("/api/advertisements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Ad\", \"description\": \"Ad description\", \"expirationDate\": \"2024-12-31\", \"email\": \"user@example.com\", \"username\": \"sampleuser\", \"password\": \"samplepassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Ad"));
    }
}
