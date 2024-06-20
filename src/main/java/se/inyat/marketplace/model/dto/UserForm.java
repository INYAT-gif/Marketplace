package se.inyat.marketplace.model.dto;

import lombok.Data;

@Data
public class UserForm {
    private String email;
    private String username;
    private String password;
}
