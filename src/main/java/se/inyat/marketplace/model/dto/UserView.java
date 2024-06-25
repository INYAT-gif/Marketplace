package se.inyat.marketplace.model.dto;

import lombok.Data;
import java.util.List;

/**
 * DTO for viewing a user.
 */
@Data
public class UserView {

    private Long id;
    private String email;
    private String username;
    private List<AdvertisementView> advertisements;
}
