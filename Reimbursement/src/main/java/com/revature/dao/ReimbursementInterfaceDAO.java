package com.revature.dao;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Reimbursement;

public interface ReimbursementInterfaceDAO {

	public List<Reimbursement> getAllReimbursements() throws SQLException;

	public Reimbursement submitRequest(String type, String description, double amount, int author, InputStream receipt) throws SQLException;

	public void updateReimbursement(int id, String status, int reimbursementId) throws SQLException;

	public Reimbursement getReimbursementById(int id) throws SQLException;

	public List<Reimbursement> getReimbursementByUserId(int userID) throws SQLException;

	public InputStream getPastTicketById(int id) throws SQLException;

}
