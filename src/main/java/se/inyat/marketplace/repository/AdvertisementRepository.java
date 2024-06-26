package se.inyat.marketplace.repository;

import se.inyat.marketplace.model.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for Advertisement entities.
 */
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    /**
     * Finds all advertisements with an expiration date after the specified date.
     *
     * @param date the date to compare with
     * @return a list of advertisements
     */
    List<Advertisement> findByExpirationDateAfter(LocalDate date);
}
