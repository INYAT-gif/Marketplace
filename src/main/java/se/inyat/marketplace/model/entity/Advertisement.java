package se.inyat.marketplace.model.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Entity representing an Advertisement.
 */
@Entity
@Data
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
