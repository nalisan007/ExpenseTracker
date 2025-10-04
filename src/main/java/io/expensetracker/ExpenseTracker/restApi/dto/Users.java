package io.expensetracker.ExpenseTracker.restApi.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "t_Users",indexes = { @Index(name="idx_user_email",columnList = "email")})
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private int userId;
	@NotBlank(message = "Email Should not be Blank")
	@Email
	@Column(unique = true)
	private String email;
	@NotBlank
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*_]).+$")
	private String password;
	@NotEmpty
	@ElementCollection
	@CollectionTable(name = "accounts",
			joinColumns = @JoinColumn(name="users_user_id",referencedColumnName = "userId"))
	@Column(name = "accounts")

	private Set<String> accounts = new LinkedHashSet<>();
	@NotEmpty
	@ElementCollection
	@CollectionTable(name = "categories", joinColumns = @JoinColumn(name="users_user_id",referencedColumnName = "userId"))
	@Column(name = "categories")

	private Set<String> categories = new LinkedHashSet<>();



	@Override
	public String toString() {
		return "Users [userId=" + userId + ", email=" + email + ", password=" + password + ", accounts=" + accounts
				+ ", category=" + categories + "]";
	}


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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
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
