package com.revature.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.dao.ReimbursementDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.FilteredStatusErrorExceptions;
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
	UserDAO userDao = new UserDAO();

	public ReimbursementService() {
		this.reimbursementDao = new ReimbursementDAO();
		this.userDao = new UserDAO();
	}

	// For mock objects
	public ReimbursementService(ReimbursementDAO reimbursementDao, UserDAO userDao) {
		this.reimbursementDao = reimbursementDao;
		this.userDao = userDao;
	}

	@Override
	public Reimbursement submitReimbursementRequest(User currentlyLoggedInUser, String type, String description,
			String amount, String mimeType, InputStream content) throws InvalidParameterException, SQLException,
			NotFoundException, SubmitFailException, FilteredStatusErrorExceptions {

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
			throw new FilteredStatusErrorExceptions(
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

			int id = Integer.parseInt(reimbursementId);
			Reimbursement reimbursement = this.reimbursementDao.getReimbursementById(id);

			if (reimbursement == null) {
				throw new NotFoundException("Reimbursement with id " + id + " cannot be found");
			}

			if (!(reimbursementStatus.contains(status))) {
				throw new InvalidParameterException("For status: only PENDING, APPROVED, or DECLINED is allowed");

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
	public InputStream getImageFromReimbursementById(User currentlyLoggedInUser, String reimburseId)
			throws UnauthorizedException, SQLException, ImageNotFoundException, InvalidParameterException {

		try {

			int reimbId = Integer.parseInt(reimburseId);

			int id = currentlyLoggedInUser.getUserId();

			if (currentlyLoggedInUser.getRole().equals("Employee")) {

				List<Reimbursement> reimbursementThatBelongToEmployee = this.reimbursementDao
						.getAllReimbursementByEmployee(id);

				Set<Integer> reimbursementIdsEncountered = new HashSet<>();

				for (Reimbursement reimbursement : reimbursementThatBelongToEmployee) {
					reimbursementIdsEncountered.add(reimbursement.getReimbId());
				}

			}

			InputStream image = this.reimbursementDao.getPastTicketById(reimbId);

			if (image == null) {
				throw new ImageNotFoundException("Image was not found for reimbursement id " + reimbId);

			}

			return image;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id supplied must be an int");

		}
	}

	@Override
	public List<Reimbursement> getPendingRequestById(User currentlyLoggedInUser, String pending)
			throws InvalidParameterException, NotFoundException, SQLException, UnauthorizedException {

		try {
			int user_Id = currentlyLoggedInUser.getUserId();

			if (currentlyLoggedInUser.getRole().equals("Employee")) {

				List<Reimbursement> reimbursementThatBelongToEmployee = this.reimbursementDao
						.getReimbursementByResolver(user_Id);

				Set<Integer> reimbursementIDsEncountered = new HashSet<>();
				for (Reimbursement reimbursement : reimbursementThatBelongToEmployee) {
					reimbursementIDsEncountered.add(reimbursement.getReimbId());
				}

			}

			List<Reimbursement> reimbursement = this.reimbursementDao.getPendingRequestById(user_Id, pending);

			if (pending == null) {

				throw new NotFoundException("Cannot find user with status" + pending);
			}

			return reimbursement;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}
	}

	@Override
	public List<Reimbursement> getReimbursementStatus(User currentlyLoggedInUser)
			throws InvalidParameterException, SQLException, UnauthorizedException, NotFoundException {

		try {

			int id = currentlyLoggedInUser.getUserId();

			if (currentlyLoggedInUser.getRole().equals("Employee")) {

				List<Reimbursement> reimbursementThatBelongToEmployee = this.reimbursementDao
						.getReimbursementByResolver(id);

				Set<Integer> reimbursementIDsEncountered = new HashSet<>();
				for (Reimbursement reimbursement : reimbursementThatBelongToEmployee) {
					reimbursementIDsEncountered.add(reimbursement.getReimbId());
				}

			}

			List<Reimbursement> reimbursement = this.reimbursementDao.getReimbursementStatusById(id);

			if (reimbursement == null) {
				throw new NotFoundException("Reimbursement not found, please try again later");
			}

			return reimbursement;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id supplied must be an int");

		}
	}

	@Override
	public Reimbursement getFilteredReimbStatus(User currentlyLoggedInUser, String filteredStatus)
			throws InvalidParameterException, NotFoundException, SQLException, UnauthorizedException,
			FilteredStatusErrorExceptions {

		try {

			Set<String> status = new HashSet<>();

			status.add("PENDING");
			status.add("APPROVED");
			status.add("DECLINED");

			if (!status.contains(filteredStatus)) {
				throw new FilteredStatusErrorExceptions(
						"Please entered the correct reimbursement status: PENDING, APPROVED or DECLINED");
			}

			if (currentlyLoggedInUser.getRole().equals("Employee")) {
				int user_Id = currentlyLoggedInUser.getUserId();

				List<Reimbursement> reimbursementThatBelongToEmployee = this.reimbursementDao
						.getReimbursementByResolver(user_Id);

				Set<Integer> reimbursementIDsEncountered = new HashSet<>();
				for (Reimbursement reimbursement : reimbursementThatBelongToEmployee) {
					reimbursementIDsEncountered.add(reimbursement.getReimbId());
				}

			}

			Reimbursement reimbursement = this.reimbursementDao.getFilteredStatus(filteredStatus);

			if (filteredStatus == null) {

				throw new NotFoundException("Cannot find user with status" + filteredStatus);
			}

			return reimbursement;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}
	}

	@Override
	public List<Reimbursement> getEmployeeReimbPastHistoy(User currentlyLoggedInUser)
			throws SQLException, InvalidParameterException, NotFoundException, UnauthorizedException {

		List<Reimbursement> reimbursements = this.reimbursementDao.getEmployeeReimbPastHistory();

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

}
