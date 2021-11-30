package com.revature.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import com.revature.exceptions.ImageNotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.NotFoundException;
import com.revature.exceptions.SubmitFailException;
import com.revature.exceptions.UnauthorizedException;
import com.revature.exceptions.reimbursementAlreadyResloved;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public interface ReimbursementServiceInterface {

	public Reimbursement submitReimbursementRequest(User currentlyLoggedInUser, String type, String description,
			String amount, String mimeType, InputStream content)
			throws InvalidParameterException, SQLException, NotFoundException, SubmitFailException;

	public List<Reimbursement> getAllReimbursement(User currentlyLoggedInUser)
			throws SQLException, InvalidParameterException, UnauthorizedException, NotFoundException;

	public Reimbursement updateReimbursement(User currentlyLoggedInUser, String reimbursementId, String status)
			throws InvalidParameterException, SQLException, NotFoundException, reimbursementAlreadyResloved;

	public InputStream getImageFromReimbursementById(User currentlyLoggedInUser, String reimbursementID)
			throws UnauthorizedException, SQLException, ImageNotFoundException, InvalidParameterException;
}
