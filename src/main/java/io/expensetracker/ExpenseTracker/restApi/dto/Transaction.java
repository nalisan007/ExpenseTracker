package io.expensetracker.ExpenseTracker.restApi.dto;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
@Table(indexes = { @Index(name = "idx_transaction_userId" , columnList = "userId") ,@Index(name = "idx_userId_dateTime" ,columnList = "userId,dateTime")  } )
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transactionId")
	private long transactionId;
	@NotNull
	@Positive
	private int userId;
	@NotBlank
	@Pattern(regexp = "^(?i)(income|expense)?", message = "Type must be either Income or Expense")
	private String type;
	@PastOrPresent
	private LocalDateTime dateTime;
	@NotBlank
	private String account;
	@NotBlank
	private String category;
	@Positive
	private float amount;
	private String note;

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", userId=" + userId + ", type=" + type + ", dateTime="
				+ dateTime + ", account=" + account + ", category=" + category + ", amount=" + amount + ", note=" + note
				+ "]";
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
