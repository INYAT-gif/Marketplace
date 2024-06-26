package se.inyat.marketplace.model.dto;

import lombok.Data;

/**
 * DTO for user authentication.
 */
@Data
public class UserForm {

    private String email;
    private String password;
}
