// src/main/java/se/inyat/marketplace/repository/AdvertisementRepository.java
package se.inyat.marketplace.repository;

import se.inyat.marketplace.model.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findByExpirationDateAfter(LocalDate date);
}
