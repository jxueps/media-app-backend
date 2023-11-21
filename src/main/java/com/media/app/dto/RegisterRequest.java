package com.media.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class RegisterRequest {
    @NotBlank(message = "Email is required.")
    @Size(min = 1, message = "Email is required.")
    @Email(message = "Email is not well formatted.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password should be at least 6 characters.")
    private String password;

    @NotBlank(message = "First name is required.")
    @Size(min = 1, message="First name is required.")
    private String firstName;

    @NotBlank(message = "First name is required.")
    @Size(min = 1, message="Last name is required.")
    private String lastName;

    public RegisterRequest(String email, String password, String firstName, String lastName) {
        this.email = email.trim();
        this.password = password.trim();
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }
}
