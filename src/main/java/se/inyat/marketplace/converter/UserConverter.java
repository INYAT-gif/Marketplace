package se.inyat.marketplace.converter;

import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.model.dto.UserView;
import org.springframework.stereotype.Component;
import se.inyat.marketplace.model.entity.User;

import java.util.stream.Collectors;

@Component
public class UserConverter {
    public UserView convertToView(User user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setEmail(user.getEmail());
        userView.setUsername(user.getUsername());
        userView.setAdvertisements(user.getAdvertisements().stream().map(ad -> {
            AdvertisementView adView = new AdvertisementView();
            adView.setId(ad.getId());
            adView.setTitle(ad.getTitle());
            adView.setDescription(ad.getDescription());
            adView.setExpirationDate(ad.getExpirationDate());
            adView.setUser(null); // Avoid recursion
            return adView;
        }).collect(Collectors.toList()));
        return userView;
    }
}
