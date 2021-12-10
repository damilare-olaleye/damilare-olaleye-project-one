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

import com.revature.dto.UserDTO;
import com.revature.model.User;
import com.revature.model.UserProfile;
import com.revature.util.JDBCUtil;

public class UserDAO implements UserInterfaceDAO {

	private Logger logger = LoggerFactory.getLogger(UserDAO.class);

	@Override
	public User getUserById(int id) throws SQLException {

		logger.info("userLogin(username, password) invoked");

		try (Connection con = JDBCUtil.getConnection()) {

			String sqlUserByIdSQL = "SELECT * FROM users WHERE user_id = ?;";

			try (PreparedStatement pstmtGetUserById = con.prepareStatement(sqlUserByIdSQL)) {
				pstmtGetUserById.setInt(1, id);

				ResultSet rs = pstmtGetUserById.executeQuery();

				if (rs.next()) {
					return new User(rs.getInt("user_id"), rs.getString("employee_username"),
							rs.getString("employee_password"), rs.getString("user_first_name"),
							rs.getString("user_last_name"), rs.getString("user_email"), rs.getString("user_role"));
				} else {
					return null;
				}
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot get user by id, please try again later");
		}

	}

	@Override
	public User userLogin(String username, String password)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {

		logger.info("userLogin(username, password) invoked");

		try (Connection con = JDBCUtil.getConnection()) {
			String userLoginSQL = "SELECT * FROM users WHERE employee_username = ? "
					+ "AND employee_password = crypt(?, employee_password);";

			try (PreparedStatement pstmtUserLogin = con.prepareStatement(userLoginSQL)) {
				pstmtUserLogin.setString(1, username);
				pstmtUserLogin.setString(2, password);

				ResultSet rs = pstmtUserLogin.executeQuery();

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

		} catch (SQLException e) {
			throw new SQLException("Cannot get user by id, please try again later");
		}

	}

	@Override
	public void deleteUser(String username, String password)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {

		logger.info("deleteUserByUsernameAndPassword(username, password");

		try (Connection con = JDBCUtil.getConnection()) {
			String sql = "DELETE FROM users WHERE employee_username = ? AND  employee_password =?";

			try (PreparedStatement pstmtDeleteUser = con.prepareStatement(sql)) {
				pstmtDeleteUser.setString(1, username);
				pstmtDeleteUser.setString(2, password);

				int numberOfRecordDeleted = pstmtDeleteUser.executeUpdate();

				if (numberOfRecordDeleted != 1) {
					throw new SQLException("Unable to delete client recored with id of " + username);
				} else {
					return;
				}

			}
		} catch (SQLException e) {
			throw new SQLException("Cannot delete user, please try again later");
		}

	}

	@Override
	public User userSignup(String username, String password, String firstName, String lastName, String email,
			String role) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {

		logger.info("userSignup(...) invoked");

		try (Connection con = JDBCUtil.getConnection()) {
			String userSignupSQL = "INSERT INTO users (employee_username, employee_password, user_first_name, user_last_name, user_email, user_role) "
					+ "VALUES (?,crypt(?, gen_salt('bf')),?,?,?,?)";

			try (PreparedStatement pstmtUserSignUp = con.prepareStatement(userSignupSQL, Statement.RETURN_GENERATED_KEYS)) {
				pstmtUserSignUp.setString(1, username);
				pstmtUserSignUp.setString(2, password);
				pstmtUserSignUp.setString(3, firstName);
				pstmtUserSignUp.setString(4, lastName);
				pstmtUserSignUp.setString(5, email);
				pstmtUserSignUp.setString(6, role);

				int numberOfRecordsInserted = pstmtUserSignUp.executeUpdate();

				if (numberOfRecordsInserted != 1) {
					throw new SQLException("Cannot add new user");
				}

				ResultSet rs = pstmtUserSignUp.getGeneratedKeys();

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
			}
		} catch (SQLException e) {
			throw new SQLException("user already exists");
		}
	}

	@Override
	public UserProfile getAllUserbyUsername(String username) throws SQLException {

		logger.info("getalluser(...) invoked");

		try (Connection con = JDBCUtil.getConnection()) {
			String getAllUserUsernameSQL = "SELECT * FROM users WHERE employee_username = ?";

			try (PreparedStatement pstmtGetUserUsername = con.prepareStatement(getAllUserUsernameSQL)) {
				pstmtGetUserUsername.setString(1, username);

				ResultSet rs = pstmtGetUserUsername.executeQuery();

				if (rs.next()) {
					String fName = rs.getString("user_first_name");
					String lName = rs.getString("user_last_name");
					String email = rs.getString("user_email");
					String userRole = rs.getString("user_role");

					return new UserProfile(fName, lName, email, userRole);

				} else {
					return null;
				}
			}

		} catch (SQLException e) {
			throw new SQLException("cannot find user with the username");
		}
	}

	@Override
	public User updateUserProfile(UserDTO dto) throws SQLException {

		logger.info("updateUserProfile(userId, dto) invoked");

		try (Connection con = JDBCUtil.getConnection()) {
			String updateUserProfileSQL = "UPDATE users SET employee_username = ?, employee_password = ?, "
					+ "user_first_name = ?, user_last_name = ?, user_role = ?, user_email = ?" + "WHERE user_id = ?;";

			try (PreparedStatement pstmtUpdateUserProfile = con.prepareStatement(updateUserProfileSQL)) {
				pstmtUpdateUserProfile.setInt(1, dto.getUserId());
				pstmtUpdateUserProfile.setString(2, dto.getUsername());
				pstmtUpdateUserProfile.setString(3, dto.getPassword());
				pstmtUpdateUserProfile.setString(4, dto.getFirstName());
				pstmtUpdateUserProfile.setString(5, dto.getLastName());
				pstmtUpdateUserProfile.setString(6, dto.getRole());
				pstmtUpdateUserProfile.setString(7, dto.getEmail());

				int numberOfRecordsUpdated = pstmtUpdateUserProfile.executeUpdate();

				if (numberOfRecordsUpdated != 1) {
					throw new SQLException("Unable to update your profile, please try again later!");

				}

				return new User(dto.getUserId(), dto.getUsername(), dto.getPassword(), dto.getFirstName(),
						dto.getLastName(), dto.getRole(), dto.getEmail());

			}
		} catch (SQLException e) {
			throw new SQLException("Cannot update user profile at this time");
		}
	}

	@Override
	public UserProfile showUserProfile(int userId) throws SQLException {

		logger.info("updateUserProfile(userId) invoked");

		try (Connection con = JDBCUtil.getConnection()) {
			String showUserProfileSQL = "SELECT employee_password , employee_username, user_first_name, user_last_name, user_role, user_email "
					+ " FROM users " + "WHERE user_id = ?;";

			try (PreparedStatement pstmtShowUserProfile = con.prepareStatement(showUserProfileSQL)) {
				pstmtShowUserProfile.setInt(1, userId);

				ResultSet rs = pstmtShowUserProfile.executeQuery();

				if (rs.next()) {

					String fName = rs.getString("user_first_name");
					String lName = rs.getString("user_last_name");
					String email = rs.getString("user_email");
					String userRole = rs.getString("user_role");

					return new UserProfile(fName, lName, email, userRole);

				} else {
					return null;
				}
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot update user profile at this time");
		}
	}

}
