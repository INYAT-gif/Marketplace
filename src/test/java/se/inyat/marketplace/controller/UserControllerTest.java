package se.inyat.marketplace.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.inyat.marketplace.model.dto.UserForm;
import se.inyat.marketplace.model.dto.UserView;
import se.inyat.marketplace.model.entity.User;
import se.inyat.marketplace.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    public void testAuthenticateUserSuccess() throws Exception {
        UserForm userForm = new UserForm();
        userForm.setEmail("test@example.com");
        userForm.setPassword("password");

        UserView userView = new UserView();
        userView.setId(1L);
        userView.setEmail("test@example.com");
        userView.setUsername("testuser");

        Mockito.when(userService.findByEmail(userForm.getEmail())).thenReturn(Optional.of(testUser));
        Mockito.when(userService.checkPassword(userForm.getPassword(), testUser.getPassword())).thenReturn(true);
        Mockito.when(userService.convertToView(testUser)).thenReturn(userView);

        mockMvc.perform(post("/api/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@example.com")))
                .andExpect(jsonPath("$.username", is("testuser")));
    }

    @Test
    public void testAuthenticateUserFailure() throws Exception {
        UserForm userForm = new UserForm();
        userForm.setEmail("test@example.com");
        userForm.setPassword("wrongpassword");

        Mockito.when(userService.findByEmail(userForm.getEmail())).thenReturn(Optional.of(testUser));
        Mockito.when(userService.checkPassword(userForm.getPassword(), testUser.getPassword())).thenReturn(false);

        mockMvc.perform(post("/api/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userForm)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        UserView userView = new UserView();
        userView.setId(1L);
        userView.setEmail("test@example.com");
        userView.setUsername("testuser");

        Mockito.when(userService.findAllUsers()).thenReturn(List.of(userView));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is("test@example.com")))
                .andExpect(jsonPath("$[0].username", is("testuser")));
    }
}
