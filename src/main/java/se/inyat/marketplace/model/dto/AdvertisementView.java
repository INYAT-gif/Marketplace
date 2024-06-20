package se.inyat.marketplace.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AdvertisementView {
    private Long id;
    private String title;
    private String description;
    private LocalDate expirationDate;
    private UserView user;
}

