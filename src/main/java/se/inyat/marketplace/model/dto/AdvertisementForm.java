package se.inyat.marketplace.model.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO for creating an advertisement.
 */
@Data
public class AdvertisementForm {

    private String title;
    private String description;
    private LocalDate expirationDate;
    private String email;
    private String username;
    private String password;
}
