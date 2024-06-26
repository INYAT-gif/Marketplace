package se.inyat.marketplace.model.entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

/**
 * Entity representing a User.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Advertisement> advertisements;

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
