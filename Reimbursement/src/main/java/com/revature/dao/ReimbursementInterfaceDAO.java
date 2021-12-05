package com.revature.dao;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Reimbursement;

public interface ReimbursementInterfaceDAO {

	public List<Reimbursement> getAllReimbursements() throws SQLException;

	public Reimbursement submitRequest(String type, String description, double amount, int author, InputStream receipt)
			throws SQLException;

	public void updateReimbursement(int reimbursementId, String status, int resolverId) throws SQLException;

	public Reimbursement getReimbursementById(int id) throws SQLException;

	public List<Reimbursement> getReimbursementByResolver(int resolverId) throws SQLException;

	public InputStream getPastTicketById(int id) throws SQLException;

	public List <Reimbursement> getPendingRequestById(int ReimbursementId, String pending) throws SQLException;

	public List <Reimbursement> getReimbursementStatusById(int rembAuthor) throws SQLException;

	public List<Reimbursement> getEmployeeReimbPastHistory() throws SQLException;

	Reimbursement getFilteredStatus(String showStatus) throws SQLException;

}
