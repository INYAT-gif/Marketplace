package se.inyat.marketplace.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.inyat.marketplace.model.dto.AdvertisementForm;
import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.model.entity.Advertisement;
import se.inyat.marketplace.model.entity.User;
import se.inyat.marketplace.service.AdvertisementService;
import se.inyat.marketplace.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@WebMvcTest(AdvertisementController.class)
public class AdvertisementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvertisementService advertisementService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setUsername("testuser");
        testUser.setPassword("password");
    }

    @Test
    public void testCreateAdvertisementWithExistingUser() throws Exception {
        AdvertisementForm form = new AdvertisementForm();
        form.setTitle("Test Title");
        form.setDescription("Test Description");
        form.setExpirationDate(LocalDate.now().plusDays(10));
        form.setEmail("test@example.com");
        form.setUsername("testuser");
        form.setPassword("password");

        AdvertisementView advertisementView = new AdvertisementView();
        advertisementView.setId(1L);
        advertisementView.setTitle("Test Title");
        advertisementView.setDescription("Test Description");
        advertisementView.setExpirationDate(LocalDate.now().plusDays(10));

        Mockito.when(userService.findByEmail(form.getEmail())).thenReturn(Optional.of(testUser));
        Mockito.when(userService.checkPassword(form.getPassword(), testUser.getPassword())).thenReturn(true);
        Mockito.when(advertisementService.saveAdvertisement(Mockito.any())).thenReturn(new Advertisement());
        Mockito.when(advertisementService.convertToView(Mockito.any())).thenReturn(advertisementView);

        mockMvc.perform(post("/api/advertisements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Title")))
                .andExpect(jsonPath("$.description", is("Test Description")));
    }

    @Test
    public void testCreateAdvertisementWithNewUser() throws Exception {
        AdvertisementForm form = new AdvertisementForm();
        form.setTitle("Test Title");
        form.setDescription("Test Description");
        form.setExpirationDate(LocalDate.now().plusDays(10));
        form.setEmail("new@example.com");
        form.setUsername("newuser");
        form.setPassword("password");

        AdvertisementView advertisementView = new AdvertisementView();
        advertisementView.setId(1L);
        advertisementView.setTitle("Test Title");
        advertisementView.setDescription("Test Description");
        advertisementView.setExpirationDate(LocalDate.now().plusDays(10));

        Mockito.when(userService.findByEmail(form.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userService.saveUser(Mockito.any())).thenReturn(testUser);
        Mockito.when(advertisementService.saveAdvertisement(Mockito.any())).thenReturn(new Advertisement());
        Mockito.when(advertisementService.convertToView(Mockito.any())).thenReturn(advertisementView);

        mockMvc.perform(post("/api/advertisements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Title")))
                .andExpect(jsonPath("$.description", is("Test Description")));
    }

    @Test
    public void testCreateAdvertisementUnauthorized() throws Exception {
        AdvertisementForm form = new AdvertisementForm();
        form.setTitle("Test Title");
        form.setDescription("Test Description");
        form.setExpirationDate(LocalDate.now().plusDays(10));
        form.setEmail("test@example.com");
        form.setUsername("testuser");
        form.setPassword("wrongpassword");

        Mockito.when(userService.findByEmail(form.getEmail())).thenReturn(Optional.of(testUser));
        Mockito.when(userService.checkPassword(form.getPassword(), testUser.getPassword())).thenReturn(false);

        mockMvc.perform(post("/api/advertisements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCreateAdvertisementInvalidInput() throws Exception {
        AdvertisementForm form = new AdvertisementForm();
        form.setTitle("");
        form.setDescription("");
        form.setExpirationDate(LocalDate.now().plusDays(10));
        form.setEmail("test@example.com");
        form.setUsername("testuser");
        form.setPassword("password");

        mockMvc.perform(post("/api/advertisements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetActiveAdvertisements() throws Exception {
        AdvertisementView advertisementView = new AdvertisementView();
        advertisementView.setId(1L);
        advertisementView.setTitle("Active Title");
        advertisementView.setDescription("Active Description");
        advertisementView.setExpirationDate(LocalDate.now().plusDays(10));

        Mockito.when(advertisementService.findActiveAdvertisements()).thenReturn(List.of(advertisementView));

        mockMvc.perform(get("/api/advertisements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Active Title")))
                .andExpect(jsonPath("$[0].description", is("Active Description")));
    }
}
