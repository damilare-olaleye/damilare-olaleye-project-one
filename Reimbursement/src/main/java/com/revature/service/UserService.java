package com.revature.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.FailedLoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.ReimbursementDAO;
import com.revature.dao.UserDAO;
import com.revature.dto.UserDTO;
import com.revature.exceptions.FailedAuthenticationException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.NotFoundException;
import com.revature.model.User;
import com.revature.model.UserProfile;

public class UserService implements UserServiceInterface {

	private Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserDAO userDao;
	private ReimbursementDAO reimbursementDao;

	// default constructor
	public UserService(UserDAO userDao) {
		this.userDao = userDao;
	}

	// Constructor
	public UserService() {
		this.userDao = new UserDAO();
		this.reimbursementDao = new ReimbursementDAO();
	}

	// For Mockito Test
	public UserService(UserDAO userDao, ReimbursementDAO reimbursementDao) {
		this.userDao = userDao;
		this.reimbursementDao = reimbursementDao;
	}

	@Override
	public User userLogin(String username, String password)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, FailedLoginException {

		logger.info("userLogin(username, password)");

		User user = this.userDao.userLogin(username, password);

		if (user == null) {
			throw new FailedLoginException("Incorrect username or password");
		}

		return user;
	}

	@Override
	public void deleteUser(String username, String password)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, NotFoundException {

		logger.info("deleteUserById(userId)");

		try {

			User user = this.userDao.userLogin(username, password);

			if (user == null) {
				throw new NotFoundException("user with username " + username + " was not found. It cannot be deleted");
			}

			if (user.getUserId() == 0) {
				this.userDao.deleteUser(username, password);
			} else {
				throw new NotFoundException("Cannot find user");
			}

			this.userDao.deleteUser(username, password);

		} catch (NumberFormatException e) {

			throw new NotFoundException("Invalid parameter was thrown");
		}

	}

	@Override
	public User userSignup(String username, String password, String firstName, String lastName, String email,
			String role) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException,
			InvalidParameterException, NotFoundException, FailedAuthenticationException {

		logger.info("userSignUp (username...) invoked");

		// roles
		Set<String> userRole = new HashSet<>();
		userRole.add("Employee");
		userRole.add("Finance Manager");

		try {

			if (!(firstName.matches("[A-Z][a-z]*"))) {
				throw new InvalidParameterException("You entered an invalid first name");
			}

			if (firstName.trim().equals("")) {
				throw new NotFoundException("first name cannot be blank");
			}

			if (!(lastName.matches("[A-Z][a-z]*"))) {
				throw new InvalidParameterException("You entered an invalid last name");
			}

			if (lastName.trim().equals("")) {
				throw new NotFoundException("last name cannot be blank");
			}

			if (username.trim().equals("")) {
				throw new NotFoundException("user name cannot be blank");
			}

			if (!(email.matches(
					"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))) {
				throw new InvalidParameterException("You entered an invalid email");
			}

			if (email.trim().equals("")) {
				throw new NotFoundException("email cannot be blank");
			}

			if (password.trim().equals("")) {
				throw new NotFoundException("email cannot be blank");
			}

			if (!(password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"))) {
				throw new InvalidParameterException(
						"Password must contain at least one number and one uppercase and lowercase letter, "
								+ "and at least 8 or more characters");

			}

			if (!(userRole.contains(role))) {
				throw new InvalidParameterException("You select an invalid role");
			}

			User user = this.userDao.userSignup(username, password, firstName, lastName, email, role);

			if (user == null) {
				throw new FailedAuthenticationException("Cannot sign up user! Try again later");
			}

			return user;

		} catch (NumberFormatException e) {

			throw new NotFoundException("Invalid parameter was thrown");
		}

	}

	@Override
	public List<UserProfile> displayUserbyNames(String names)
			throws NotFoundException, SQLException, InvalidParameterException {

		logger.info("displayUserbyNames (names...) invoked");

		try {

			List<UserProfile> user = this.userDao.getAllUserbyNames("%" + names + "%");

			if (user == null) {
				throw new NotFoundException("Cannot find user");
			}

			return user;

		} catch (NumberFormatException e) {

			throw new InvalidParameterException("Invalid parameter was thrown");
		}

	}

	@Override
	public User updateUserProfile(User currentlyLoggedInUser, String username, String password, String firstName,
			String lastName, String role, String email)
			throws InvalidParameterException, SQLException, NotFoundException {

		try {

			int user_Id = currentlyLoggedInUser.getUserId();

			User userProfileToEdit = this.userDao.getUserById(user_Id);

			if (userProfileToEdit == null) {
				throw new NotFoundException("Cannot update profile at this time, please try again later!");
			}

			UserDTO dto = new UserDTO(user_Id, username, password, firstName, lastName, role, email);

			User updatedUser = this.userDao.updateUserProfile(dto);

			return updatedUser;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("The Id provided is not int convertible value");
		}
	}

	@Override
	public UserProfile displayUserProfile(User currentlyLoggedInUser)
			throws NotFoundException, SQLException, InvalidParameterException {

		logger.info("displayAllUserbyUsername (username...) invoked");

		try {

			int user_Id = currentlyLoggedInUser.getUserId();

			UserProfile user = this.userDao.showUserProfile(user_Id);

			if (user == null) {
				throw new NotFoundException("Cannot find user");
			}

			return user;

		} catch (NumberFormatException e) {

			throw new InvalidParameterException("Invalid parameter was thrown");
		}

	}
}
