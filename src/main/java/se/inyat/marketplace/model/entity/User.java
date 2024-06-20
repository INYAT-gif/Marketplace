// src/main/java/se/inyat/marketplace/model/User.java
package se.inyat.marketplace.model.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Advertisement> advertisements;
}

