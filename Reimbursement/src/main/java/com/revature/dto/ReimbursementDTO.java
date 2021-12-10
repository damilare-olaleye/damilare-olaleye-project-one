package com.revature.dto;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Objects;

public class ReimbursementDTO {

	private int reimbId;
	private Timestamp submitted;
	private Timestamp resolved;
	private String status;
	private String type;
	private String description;
	private int amount;
	private InputStream receipt;
	private int author;
	private int resolver;
	private int userId;

	public ReimbursementDTO() {
	}

	public ReimbursementDTO(int reimbId, Timestamp submitted, Timestamp resolved, String status, String type,
			String description, int amount, InputStream receipt, int author, int resolver, int userId) {
		super();
		this.reimbId = reimbId;
		this.submitted = submitted;
		this.resolved = resolved;
		this.status = status;
		this.type = type;
		this.description = description;
		this.amount = amount;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.userId = userId;
	}

	public int getReimbId() {
		return reimbId;
	}

	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public InputStream getReceipt() {
		return receipt;
	}

	public void setReceipt(InputStream receipt) {
		this.receipt = receipt;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public int getResolver() {
		return resolver;
	}

	public void setResolver(int resolver) {
		this.resolver = resolver;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, author, description, receipt, reimbId, resolved, resolver, status, submitted, type,
				userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementDTO other = (ReimbursementDTO) obj;
		return amount == other.amount && author == other.author && Objects.equals(description, other.description)
				&& Objects.equals(receipt, other.receipt) && reimbId == other.reimbId
				&& Objects.equals(resolved, other.resolved) && resolver == other.resolver
				&& Objects.equals(status, other.status) && Objects.equals(submitted, other.submitted)
				&& Objects.equals(type, other.type) && userId == other.userId;
	}

	@Override
	public String toString() {
		return "ReimbursementDTO [reimbId=" + reimbId + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", status=" + status + ", type=" + type + ", description=" + description + ", amount=" + amount
				+ ", receipt=" + receipt + ", author=" + author + ", resolver=" + resolver + ", userId=" + userId + "]";
	}

}