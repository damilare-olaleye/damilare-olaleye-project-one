package com.revature.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

import com.revature.dto.UserDTO;
import com.revature.model.User;
import com.revature.model.UserProfile;

public interface UserInterfaceDAO {

	public User userLogin(String username, String password)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException;

	public void deleteUser(String username, String password)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException;

	public User userSignup(String username, String password, String firstName, String lastName, String email,
			String role) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException;

	public List<UserProfile> getAllUserbyNames(String firstname) throws SQLException;

	User getUserById(int id) throws SQLException;

	User updateUserProfile(UserDTO dto) throws SQLException;

	UserProfile showUserProfile(int userId) throws SQLException;
}
