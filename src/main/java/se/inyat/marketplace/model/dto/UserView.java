// src/main/java/se/inyat/marketplace/dto/UserViewDTO.java
package se.inyat.marketplace.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserView {
    private Long id;
    private String email;
    private String username;
    private List<AdvertisementView> advertisements;
}
