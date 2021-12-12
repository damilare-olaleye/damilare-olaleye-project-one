package com.revature.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

import javax.security.auth.login.FailedLoginException;

import com.revature.exceptions.FailedAuthenticationException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.NotFoundException;
import com.revature.model.User;
import com.revature.model.UserProfile;

public interface UserServiceInterface {

	public User userLogin(String username, String password)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, FailedLoginException;

	public void deleteUser(String username, String password)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, NotFoundException;

	public User userSignup(String username, String password, String firstName, String lastName, String email,
			String role) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException,
			InvalidParameterException, NotFoundException, FailedAuthenticationException;

	public List<UserProfile> displayUserbyNames(String names)
			throws NotFoundException, SQLException, InvalidParameterException;

	public User updateUserProfile(User currentlyLoggedInUser, String username, String password, String firstName,
			String lastName, String role, String email)
			throws InvalidParameterException, SQLException, NotFoundException;

	UserProfile displayUserProfile(User currentlyLoggedInUser)
			throws NotFoundException, SQLException, InvalidParameterException;
}
