package io.expensetracker.ExpenseTracker.restApi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

@Component
public class RegisterCredentials {
    @NotBlank(message = "Email Should not be Blank")
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_]).+$")
    private String password;
}
