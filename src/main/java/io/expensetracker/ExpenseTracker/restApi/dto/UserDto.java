package io.expensetracker.ExpenseTracker.restApi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class UserDto {
    @Positive
    private int userId;
    @NotBlank(message = "Email Should not be Blank")
    @Email
    private String email;
    @NotEmpty
    private Set<String> accounts = new LinkedHashSet<>();
    @NotEmpty
    private Set<String> categories = new LinkedHashSet<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<String> accounts) {
        this.accounts = accounts;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }
}
