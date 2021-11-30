package com.revature.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.model.User;
import com.revature.model.UserProfile;
import com.revature.util.JDBCUtil;

public class UserDAO implements UserInterfaceDAO {

	private Logger logger = LoggerFactory.getLogger(UserDAO.class);

	@Override
	public User userLogin(String username, String password)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {

		logger.info("userLogin(username, password) invoked");

		try (Connection con = JDBCUtil.getConnection()) {
			String sql = "SELECT * FROM users WHERE employee_username = ? "
					+ "AND employee_password = crypt(?, employee_password);";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("user_id");
				String userName = rs.getString("employee_username");
				String pass = "######################";
				String firstName = rs.getString("user_first_name");
				String lastName = rs.getString("user_last_name");
				String email = rs.getString("user_email");
				String userRole = rs.getString("user_role");

				return new User(id, userName, pass, firstName, lastName, email, userRole);

			} else {
				return null;
			}
		}

	}

	@Override
	public void deleteUser(String username, String password)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {

		logger.info("deleteUserByUsernameAndPassword(username, password");

		try (Connection con = JDBCUtil.getConnection()) {
			String sql = "DELETE FROM users WHERE employee_username = ? AND  employee_password =?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			int numberOfRecordDeleted = pstmt.executeUpdate();

			if (numberOfRecordDeleted != 1) {
				throw new SQLException("Unable to delete client recored with id of " + username);
			} else {
				return;
			}

		}

	}

	@Override
	public User userSignup(String username, String password, String firstName, String lastName, String email,
			String role) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {

		logger.info("addUser(...) invoked");

		try (Connection con = JDBCUtil.getConnection()) {
			String sql = "INSERT INTO users (employee_username, employee_password, user_first_name, user_last_name, user_email, user_role) "
					+ "VALUES (?,crypt(?, gen_salt('bf')),?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, firstName);
			pstmt.setString(4, lastName);
			pstmt.setString(5, email);
			pstmt.setString(6, role);

			int numberOfRecordsInserted = pstmt.executeUpdate();

			if (numberOfRecordsInserted != 1) {
				throw new SQLException("Cannot add new user");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
				int id = rs.getInt("user_id");
				String userName = rs.getString("employee_username");
				String pass = "######################";
				String fName = rs.getString("user_first_name");
				String lName = rs.getString("user_last_name");
				String userEmail = rs.getString("user_email");
				String userRole = rs.getString("user_role");

				return new User(id, userName, pass, fName, lName, userEmail, userRole);

			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new SQLException("user already exists");
		}
	}
	
	public UserProfile getAllUserbyUsername(String username) throws SQLException {
		
		logger.info("getalluser(...) invoked");
		
		try (Connection con = JDBCUtil.getConnection()) {
			String sql = "SELECT * FROM users WHERE employee_username = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, username);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String fName = rs.getString("user_first_name");
				String lName = rs.getString("user_last_name");
				String email = rs.getString("user_email");
				String userRole = rs.getString("user_role");

				return new UserProfile(fName, lName, email, userRole);

			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new SQLException("cannot find user with the username");
		}
	}

}
