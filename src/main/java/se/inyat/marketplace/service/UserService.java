package se.inyat.marketplace.service;

import se.inyat.marketplace.model.dto.AdvertisementView;
import se.inyat.marketplace.model.dto.UserView;
import se.inyat.marketplace.model.entity.User;
import se.inyat.marketplace.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.inyat.marketplace.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<UserView> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToView).collect(Collectors.toList());
    }

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
