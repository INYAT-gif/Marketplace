package se.inyat.marketplace.model.entity;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Entity representing an Advertisement.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user") //why to exclude user?
@EqualsAndHashCode
@Builder

@Entity
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

    public Advertisement(String title, String description, LocalDate expirationDate, User user) {
        this.title = title;
        this.description = description;
        this.expirationDate = expirationDate;
        this.user = user;
    }
}
