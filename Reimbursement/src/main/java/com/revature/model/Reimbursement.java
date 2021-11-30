package com.revature.model;

import java.io.InputStream;
import java.util.Objects;

public class Reimbursement {

	private int reimbId;
	private String submitted;
	private String resolved;
	private String status;
	private String type;
	private String description;
	private double amount;
	private int author;
	private int resolver;
	private int userId;

	public Reimbursement() {
	}

	public Reimbursement(int reimbId, String submitted, String resolved, String status, String type, String description,
			double amount, int author, int resolver, int userId) {
		super();
		this.reimbId = reimbId;
		this.submitted = submitted;
		this.resolved = resolved;
		this.status = status;
		this.type = type;
		this.description = description;
		this.amount = amount;
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

	public String getSubmitted() {
		return submitted;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	public String getResolved() {
		return resolved;
	}

	public void setResolved(String resolved) {
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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
		return Objects.hash(amount, author, description, reimbId, resolved, resolver, status, submitted, type, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount) && author == other.author
				&& Objects.equals(description, other.description) && reimbId == other.reimbId
				&& Objects.equals(resolved, other.resolved) && resolver == other.resolver
				&& Objects.equals(status, other.status) && Objects.equals(submitted, other.submitted)
				&& Objects.equals(type, other.type) && userId == other.userId;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbId=" + reimbId + ", submitted=" + submitted + ", resolved=" + resolved + ", status="
				+ status + ", type=" + type + ", description=" + description + ", amount=" + amount + ", author="
				+ author + ", resolver=" + resolver + ", userId=" + userId + "]";
	}

	
}
