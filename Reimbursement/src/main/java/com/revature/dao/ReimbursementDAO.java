package com.revature.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.model.Reimbursement;
import com.revature.util.JDBCUtil;

public class ReimbursementDAO implements ReimbursementInterfaceDAO {

	private Logger logger = LoggerFactory.getLogger(ReimbursementDAO.class);

	@Override
	public List<Reimbursement> getAllReimbursements() throws SQLException {

		logger.info("getAllReimbursements() invoked");

		List<Reimbursement> reimbursements = new ArrayList<>();

		try (Connection con = JDBCUtil.getConnection()) {

			String sql = "SELECT * FROM reimbursement";

			PreparedStatement pstmt = con.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				reimbursements.add(new Reimbursement(rs.getInt("reimbursement_id"),
						rs.getString("reimbursement_submitted"), rs.getString("reimbursement_resolved"),
						rs.getString("reimbursement_status"), rs.getString("reimbursement_type"),
						rs.getString("reimbursement_description"), rs.getInt("reimbursement_amount"),
						rs.getInt("reimbursement_author"), rs.getInt("reimbursement_resolver"), rs.getInt("user_id")));
			}

			return reimbursements;

		}
	}

	@Override
	public Reimbursement submitRequest(String type, String description, double amount, int author, InputStream receipt)
			throws SQLException {

		logger.info("submitRequest(...) invoked");

		try (Connection con = JDBCUtil.getConnection()) {
			String sql = "INSERT INTO reimbursement "
					+ "(reimbursement_type, reimbursement_amount, reimbursement_description, reimbursement_submitted, reimbursement_author, reimbursement_receipt) "
					+ "VALUES (?,?,?,now(),?,?);";

			con.setAutoCommit(false);

			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, type);
			pstmt.setDouble(2, amount);
			pstmt.setString(3, description);
			pstmt.setInt(4, author);
			pstmt.setBinaryStream(5, receipt);

			int numberOfInsertedRecords = pstmt.executeUpdate();

			if (numberOfInsertedRecords != 1) {
				throw new SQLException("Issue occurred when adding reimbursement");
			}

			ResultSet rs = pstmt.getGeneratedKeys();

			rs.next();
			int generatedId = rs.getInt(1);

			con.commit();

			return new Reimbursement(generatedId, rs.getString("reimbursement_submitted"),
					rs.getString("reimbursement_resolved"), description, type, rs.getString("reimbursement_status"),
					amount, author, 0, rs.getInt("user_id"));

		}
	}

	@Override
	public void updateReimbursement(int id, String status, int reimbursementId) throws SQLException {

		try (Connection con = JDBCUtil.getConnection()) {

			String sql = "UPDATE reimbursement SET reimbursement_status = ?, "
					+ "reimbursement_resolved = now(), reimbursement_resolver = ? " + "WHERE reimbursement_id = ?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, status);
			pstmt.setInt(2, id);
			pstmt.setInt(3, reimbursementId);

			int updateReimbursements = pstmt.executeUpdate();

			if (updateReimbursements != 1) {
				throw new SQLException("Cannot update reimbursement");
			}
		}
	}

	@Override
	public Reimbursement getReimbursementById(int id) throws SQLException {

		try (Connection con = JDBCUtil.getConnection()) {

			String sql = "SELECT * FROM Reimbursement WHERE reimbursement_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new Reimbursement(rs.getInt("reimbursement_id"), rs.getString("reimbursement_submitted"),
						rs.getString("reimbursement_resolved"), rs.getString("reimbursement_status"),
						rs.getString("reimbursement_type"), rs.getString("reimbursement_description"),
						rs.getInt("reimbursement_amount"), rs.getInt("reimbursement_author"),
						rs.getInt("reimbursement_resolver"), rs.getInt("user_id"));
			} else {
				return null;
			}

		}
	}

	@Override
	public List<Reimbursement> getReimbursementByUserId(int userID) throws SQLException {

		try (Connection con = JDBCUtil.getConnection()) {
			List<Reimbursement> reimbursement = new ArrayList<>();

			String sql = "SELECT reimbursement_id, reimbursement_submitted, reimbursement_resolved, reimbursement_status, "
					+ "reimbursement_type, reimbursement_description, reimbursement_amount, "
					+ "reimbursement_receipt, reimbursement_author, reimbursement_resolver,"
					+ "user_id FROM reimbursement WHERE user_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userID);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				reimbursement.add(new Reimbursement(rs.getInt("reimbursement_id"),
						rs.getString("reimbursement_submitted"), rs.getString("reimbursement_resolved"),
						rs.getString("reimbursement_status"), rs.getString("reimbursement_type"),
						rs.getString("reimbursement_description"), rs.getInt("reimbursement_amount"),
						rs.getInt("reimbursement_author"), rs.getInt("reimbursement_resolver"), rs.getInt("user_id")));
			}

			return reimbursement;
		}
	}

	@Override
	public InputStream getPastTicketById(int id) throws SQLException {

		logger.info("getPastTicketById(id");

		try (Connection con = JDBCUtil.getConnection()) {

			String sql = "SELECT reimbursement_receipt FROM reimbursement WHERE reimbursement_id = ?";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				InputStream image = rs.getBinaryStream("reimbursement_receipt");

				return image;
			}

			return null;

		}

	}

	public Reimbursement getPendingRequestById(String pending) throws SQLException {

		logger.info("getPendingRequest(int id)");

		try (Connection con = JDBCUtil.getConnection()) {

			String sql = "SELECT reimbursement_status FROM reimbursement WHERE reimbursement_status = ? AND reimbursement_id =?;";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, pending);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new Reimbursement(rs.getInt("reimbursement_id"), rs.getString("reimbursement_submitted"),
						rs.getString("reimbursement_resolved"), rs.getString("reimbursement_status"),
						rs.getString("reimbursement_type"), rs.getString("reimbursement_description"),
						rs.getInt("reimbursement_amount"), rs.getInt("reimbursement_author"),
						rs.getInt("reimbursement_resolver"), rs.getInt("user_id"));
			} else {
				return null;
			}

		}

	}

}
