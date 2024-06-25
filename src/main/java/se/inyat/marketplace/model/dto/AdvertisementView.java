package se.inyat.marketplace.model.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * DTO for viewing an advertisement.
 */
@Data
public class AdvertisementView {

    private Long id;
    private String title;
    private String description;
    private LocalDate expirationDate;
    private UserView user;
}
