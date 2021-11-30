package com.revature.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.dao.ReimbursementDAO;
import com.revature.exceptions.ImageNotFoundException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.NotFoundException;
import com.revature.exceptions.SubmitFailException;
import com.revature.exceptions.UnauthorizedException;
import com.revature.exceptions.reimbursementAlreadyResloved;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public class ReimbursementService implements ReimbursementServiceInterface {

	ReimbursementDAO reimbursementDao = new ReimbursementDAO();

	public ReimbursementService() {
		this.reimbursementDao = new ReimbursementDAO();
	}

	public ReimbursementService(ReimbursementDAO reimbursementDao) {
		this.reimbursementDao = reimbursementDao;
	}

	@Override
	public Reimbursement submitReimbursementRequest(User currentlyLoggedInUser, String type, String description,
			String amount, String mimeType, InputStream content) throws InvalidParameterException, SQLException, NotFoundException, SubmitFailException {
			
			Set<String> reimbursementTypes = new HashSet<>();
			
			double submitAmount = Double.parseDouble(amount);

			reimbursementTypes.add("LODGING");
			reimbursementTypes.add("TRAVEL");
			reimbursementTypes.add("FOOD");
			reimbursementTypes.add("OTHER");
			
			Set<String> allowedFileTypes = new HashSet<>();
			allowedFileTypes.add("image/jpeg");
			allowedFileTypes.add("image/png");

			if (!(reimbursementTypes.contains(type))) {
				throw new InvalidParameterException(
						"When adding an reimbursements: LODGING, only TRAVEL, FOOD, or OTHER are allowed");

			}
			
			if (!allowedFileTypes.contains(mimeType)) {
				throw new InvalidParameterException("When adding an assignment image, only PNG, JPEG, or GIF are allowed");
			}

			int userId = currentlyLoggedInUser.getUserId();
			

			return this.reimbursementDao.submitRequest(type, description, submitAmount, userId, content);
			
		


	}

	@Override
	public List<Reimbursement> getAllReimbursement(User currentlyLoggedInUser)
			throws SQLException, InvalidParameterException, UnauthorizedException, NotFoundException {

		List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimbursements();

		try {

			if (reimbursements == null) {
				throw new NotFoundException("No reimbursement available");
			}

			if (currentlyLoggedInUser.getRole().equals("Employee")) {
				throw new UnauthorizedException("You do not have access to this, sign in as a Finance Manager");
			} else {

				return reimbursements;

			}

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id supplied must be an int");

		}
	}

	@Override
	public Reimbursement updateReimbursement(User currentlyLoggedInUser, String reimbursementId, String status)
			throws InvalidParameterException, SQLException, NotFoundException, reimbursementAlreadyResloved {

		try {

			Set<String> reimbursementStatus = new HashSet<>();

			reimbursementStatus.add("PENDING");
			reimbursementStatus.add("APPROVED");
			reimbursementStatus.add("DECLINED");

			if (!(reimbursementStatus.contains(status))) {
				throw new InvalidParameterException(
						"When adding an reimbursements status: only PENDING, APPROVED, or DECLINED are allowed");

			}

			int id = Integer.parseInt(reimbursementId);
			Reimbursement reimbursement = this.reimbursementDao.getReimbursementById(id);

			if (reimbursement == null) {
				throw new NotFoundException("Reimbursement with id " + id + " cannot be found");
			}

			if (reimbursement.getResolver() == 0) {
				this.reimbursementDao.updateReimbursement(id, status, currentlyLoggedInUser.getUserId());
			} else {
				throw new reimbursementAlreadyResloved("Reimbursement has already been resolved");
			}

			return this.reimbursementDao.getReimbursementById(id);

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("The id provided is not an int");
		}
	}

	@Override
	public InputStream getImageFromReimbursementById(User currentlyLoggedInUser, String reimbursementID)
			throws UnauthorizedException, SQLException, ImageNotFoundException, InvalidParameterException {

		try {

			int id = Integer.parseInt(reimbursementID);

			if (currentlyLoggedInUser.getRole().equals("Employee")) {
				int userId = currentlyLoggedInUser.getUserId();
				List<Reimbursement> reimbursementThatBelongToEmployee = this.reimbursementDao
						.getReimbursementByUserId(userId);

				Set<Integer> reimbursementIDsEncountered = new HashSet<>();
				for (Reimbursement reimbursement : reimbursementThatBelongToEmployee) {
					reimbursementIDsEncountered.add(reimbursement.getReimbId());
				}

				if (!reimbursementIDsEncountered.contains(id)) {
					throw new UnauthorizedException(
							"You cannot access the images of reimbursements that do not belong to yourself");

				}
			}

			InputStream image = this.reimbursementDao.getPastTicketById(id);

			if (image == null) {
				throw new ImageNotFoundException("Image was not found for reimbursement id " + id);

			}

			return image;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id supplied must be an int");

		}
	}

	public Reimbursement getPendingRequestById(User currentlyLoggedInUser, String pending, String userId)
			throws InvalidParameterException, NotFoundException, SQLException, UnauthorizedException {

		try {

			int id = Integer.parseInt(userId);

			if (currentlyLoggedInUser.getRole().equals("Employee")) {
				int user_Id = currentlyLoggedInUser.getUserId();

				List<Reimbursement> reimbursementThatBelongToEmployee = this.reimbursementDao
						.getReimbursementByUserId(user_Id);

				Set<Integer> reimbursementIDsEncountered = new HashSet<>();
				for (Reimbursement reimbursement : reimbursementThatBelongToEmployee) {
					reimbursementIDsEncountered.add(reimbursement.getReimbId());
				}

				if (!reimbursementIDsEncountered.contains(id)) {
					throw new UnauthorizedException(
							"You cannot access the pending request of reimbursements that do not belong to you");
				}

			}

			Reimbursement reimbursement = this.reimbursementDao.getPendingRequestById(pending);

			if (pending == null) {

				throw new NotFoundException("Cannot find user with status" + pending);
			}

			return reimbursement;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}
	}

}
