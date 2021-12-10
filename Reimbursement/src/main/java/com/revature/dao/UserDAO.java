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

			String sql = "SELECT * FROM users WHERE user_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new User(rs.getInt("user_id"), rs.getString("employee_username"),
						rs.getString("employee_password"), rs.getString("user_first_name"),
						rs.getString("user_last_name"), rs.getString("user_email"), rs.getString("user_role"));
			} else {
				return null;
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

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			int numberOfRecordDeleted = pstmt.executeUpdate();

			if (numberOfRecordDeleted != 1) {
				throw new SQLException("Unable to delete client recored with id of " + username);
			} else {
				return;
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

	@Override
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

	@Override
	public User updateUserProfile(UserDTO dto) throws SQLException {

		logger.info("updateUserProfile(userId, dto) invoked");

		try (Connection con = JDBCUtil.getConnection()) {
			String sql = "UPDATE users SET employee_username = ?, employee_password = ?, "
					+ "user_first_name = ?, user_last_name = ?, user_role = ?, user_email = ?" + "WHERE user_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, dto.getUserId());
			pstmt.setString(2, dto.getUsername());
			pstmt.setString(3, dto.getPassword());
			pstmt.setString(4, dto.getFirstName());
			pstmt.setString(5, dto.getLastName());
			pstmt.setString(6, dto.getRole());
			pstmt.setString(7, dto.getEmail());

			int numberOfRecordsUpdated = pstmt.executeUpdate();

			if (numberOfRecordsUpdated != 1) {
				throw new SQLException("Unable to update your profile, please try again later!");

			}

			return new User(dto.getUserId(), dto.getUsername(), dto.getPassword(), dto.getFirstName(),
					dto.getLastName(), dto.getRole(), dto.getEmail());

		} catch (SQLException e) {
			throw new SQLException("Cannot update user profile at this time");
		}
	}

	@Override
	public UserProfile showUserProfile(int userId) throws SQLException {

		logger.info("updateUserProfile(userId) invoked");

		try (Connection con = JDBCUtil.getConnection()) {
			String sql = "SELECT employee_password , employee_username, user_first_name, user_last_name, user_role, user_email "
					+ " FROM users " + "WHERE user_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, userId);

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
			throw new SQLException("Cannot update user profile at this time");
		}
	}

}