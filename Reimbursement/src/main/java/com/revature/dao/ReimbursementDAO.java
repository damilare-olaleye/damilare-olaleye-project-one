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

			String getAllReimbSQL = "SELECT * FROM reimbursement ORDER BY reimbursement_status";

			try (PreparedStatement pstmtAllReimb = con.prepareStatement(getAllReimbSQL)) {
				ResultSet rs = pstmtAllReimb.executeQuery();

				while (rs.next()) {

					reimbursements.add(new Reimbursement(rs.getInt("reimbursement_id"),
							rs.getString("reimbursement_submitted"), rs.getString("reimbursement_resolved"),
							rs.getString("reimbursement_status"), rs.getString("reimbursement_type"),
							rs.getString("reimbursement_description"), rs.getInt("reimbursement_amount"),
							rs.getInt("reimbursement_author"), rs.getInt("reimbursement_resolver")));
				}

				return reimbursements;
			}

		} catch (SQLException e) {
			throw new SQLException("No reimbursement is available at this time, please try again!");
		}
	}

	@Override
	public Reimbursement submitRequest(String type, String description, double amount, int author, InputStream receipt)
			throws SQLException {

		logger.info("submitRequest(...) invoked");

		try (Connection con = JDBCUtil.getConnection()) {
			String submitRequestSQL = "INSERT INTO reimbursement "
					+ "(reimbursement_type, reimbursement_amount, reimbursement_description, reimbursement_submitted, reimbursement_author, reimbursement_receipt) "
					+ "VALUES (?,?,?,date_trunc('second',now()::timestamp),?,?);";

			con.setAutoCommit(false);

			try (PreparedStatement pstmtSubmitRequest = con.prepareStatement(submitRequestSQL,
					Statement.RETURN_GENERATED_KEYS)) {

				pstmtSubmitRequest.setString(1, type);
				pstmtSubmitRequest.setDouble(2, amount);
				pstmtSubmitRequest.setString(3, description);
				pstmtSubmitRequest.setInt(4, author);
				pstmtSubmitRequest.setBinaryStream(5, receipt);

				int numberOfInsertedRecords = pstmtSubmitRequest.executeUpdate();

				if (numberOfInsertedRecords != 1) {
					throw new SQLException("Issue occurred when adding reimbursement");
				}

				ResultSet rs = pstmtSubmitRequest.getGeneratedKeys();

				rs.next();
				int generatedId = rs.getInt(1);

				con.commit();

				return new Reimbursement(generatedId, rs.getString("reimbursement_submitted"),
						rs.getString("reimbursement_resolved"), description, type, rs.getString("reimbursement_status"),
						amount, author, rs.getInt("reimbursement_resolver"));
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot complete request this time, please try again later!");
		}
	}

	@Override
	public void updateReimbursement(int reimbursementId, String status, int resolver) throws SQLException {

		try (Connection con = JDBCUtil.getConnection()) {

			String updateReimbSQL = "UPDATE reimbursement SET reimbursement_status = ?, "
					+ "reimbursement_resolved = date_trunc('second',now()::timestamp), reimbursement_resolver = ? "
					+ "WHERE reimbursement_id = ?;";

			try (PreparedStatement pstmtUpdateReimbu = con.prepareStatement(updateReimbSQL)) {

				pstmtUpdateReimbu.setString(1, status);
				pstmtUpdateReimbu.setInt(2, resolver);
				pstmtUpdateReimbu.setInt(3, reimbursementId);

				int updateReimbursements = pstmtUpdateReimbu.executeUpdate();

				if (updateReimbursements != 1) {
					throw new SQLException("Cannot complete request this time, please try again later!");
				}
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot complete request this time, please try again later!");
		}
	}

	@Override
	public Reimbursement getReimbursementById(int id) throws SQLException {

		try (Connection con = JDBCUtil.getConnection()) {

			String getReimbByIdSQL = "SELECT reimbursement_id, reimbursement_submitted, reimbursement_resolved, reimbursement_status, reimbursement_type, "
					+ " reimbursement_description, reimbursement_amount, reimbursement_author,  reimbursement_resolver "
					+ "   FROM Reimbursement WHERE reimbursement_id = ?";

			try (PreparedStatement pstmtGetReimbById = con.prepareStatement(getReimbByIdSQL)) {

				pstmtGetReimbById.setInt(1, id);

				ResultSet rs = pstmtGetReimbById.executeQuery();

				if (rs.next()) {
					return new Reimbursement(rs.getInt("reimbursement_id"), rs.getString("reimbursement_submitted"),
							rs.getString("reimbursement_resolved"), rs.getString("reimbursement_status"),
							rs.getString("reimbursement_type"), rs.getString("reimbursement_description"),
							rs.getInt("reimbursement_amount"), rs.getInt("reimbursement_author"),
							rs.getInt("reimbursement_resolver"));
				} else {
					return null;
				}
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot complete request this time, please try again later");
		}
	}

	@Override
	public List<Reimbursement> getReimbursementByResolver(int resolverId) throws SQLException {

		try (Connection con = JDBCUtil.getConnection()) {
			List<Reimbursement> reimbursement = new ArrayList<>();

			String getReimbByResolverSQL = "SELECT reimbursement_id, reimbursement_submitted, reimbursement_resolved, reimbursement_status, "
					+ "reimbursement_type, reimbursement_description, reimbursement_amount, "
					+ "reimbursement_receipt, reimbursement_author "
					+ " FROM reimbursement WHERE reimbursement_resolver = ?";

			try (PreparedStatement pstmtgetReimbByResolver = con.prepareStatement(getReimbByResolverSQL)) {

				pstmtgetReimbByResolver.setInt(1, resolverId);

				ResultSet rs = pstmtgetReimbByResolver.executeQuery();

				while (rs.next()) {

					reimbursement.add(new Reimbursement(rs.getInt("reimbursement_id"),
							rs.getString("reimbursement_submitted"), rs.getString("reimbursement_resolved"),
							rs.getString("reimbursement_status"), rs.getString("reimbursement_type"),
							rs.getString("reimbursement_description"), rs.getInt("reimbursement_amount"),
							rs.getInt("reimbursement_author"), rs.getInt("reimbursement_resolver")));
				}

				return reimbursement;
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot complete request this time, please try again later!");
		}
	}

	@Override
	public List<Reimbursement> getAllReimbursementByEmployee(int resolverId) throws SQLException {

		try (Connection con = JDBCUtil.getConnection()) {

			List<Reimbursement> reimbursement = new ArrayList<>();

			String getAllReimbByEmpSQL = "SELECT * FROM reimbursement WHERE reimbursement_resolver = ?;";

			try (PreparedStatement pstmtGetAllReimbByEmp = con.prepareStatement(getAllReimbByEmpSQL)) {
				pstmtGetAllReimbByEmp.setInt(1, resolverId);

				ResultSet rs = pstmtGetAllReimbByEmp.executeQuery();

				while (rs.next()) {

					reimbursement.add(new Reimbursement(rs.getInt("reimbursement_id"),
							rs.getString("reimbursement_submitted"), rs.getString("reimbursement_resolved"),
							rs.getString("reimbursement_status"), rs.getString("reimbursement_type"),
							rs.getString("reimbursement_description"), rs.getInt("reimbursement_amount"),
							rs.getInt("reimbursement_author"), rs.getInt("reimbursement_resolver")));
				}

				return reimbursement;
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot complete request this time, please try again later");
		}

	}

	@Override
	public InputStream getPastTicketById(int id) throws SQLException {

		logger.info("getPastTicketById(id");

		try (Connection con = JDBCUtil.getConnection()) {

			String getPastTicktSQL = "SELECT reimbursement_receipt FROM reimbursement WHERE reimbursement_id = ?;";

			try (PreparedStatement pstmtGetPastTickById = con.prepareStatement(getPastTicktSQL)) {
				pstmtGetPastTickById.setInt(1, id);

				ResultSet rs = pstmtGetPastTickById.executeQuery();

				if (rs.next()) {
					InputStream image = rs.getBinaryStream("reimbursement_receipt");

					return image;
				}

				return null;
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot complete request this time, please try again later");
		}

	}

	@Override
	public List<Reimbursement> getPendingRequestById(int ReimbursementId, String pending) throws SQLException {

		logger.info("getPendingRequestById(int ReimbursementId, pending)");

		List<Reimbursement> reimbursement = new ArrayList<>();

		try (Connection con = JDBCUtil.getConnection()) {

			String getPendingReqSQL = "SELECT reimbursement_id, reimbursement_submitted, reimbursement_resolved, "
					+ "reimbursement_status, reimbursement_type, reimbursement_description,  "
					+ " reimbursement_amount,reimbursement_author, reimbursement_resolver "
					+ " FROM reimbursement WHERE reimbursement_author = ? AND reimbursement_status = ?;";

			try (PreparedStatement pstmtGetPendingReqId = con.prepareStatement(getPendingReqSQL)) {
				pstmtGetPendingReqId.setInt(1, ReimbursementId);
				pstmtGetPendingReqId.setString(2, pending);

				ResultSet rs = pstmtGetPendingReqId.executeQuery();

				while (rs.next()) {

					reimbursement.add(new Reimbursement(rs.getInt("reimbursement_id"),
							rs.getString("reimbursement_submitted"), rs.getString("reimbursement_resolved"),
							rs.getString("reimbursement_status"), rs.getString("reimbursement_type"),
							rs.getString("reimbursement_description"), rs.getInt("reimbursement_amount"),
							rs.getInt("reimbursement_author"), rs.getInt("reimbursement_resolver")));
				}

				return reimbursement;
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot complete this task at this time, please try again later!");
		}
	}

	@Override
	public List<Reimbursement> getReimbursementStatusById(int rembAuthor) throws SQLException {

		logger.info("getReimbursementStatusById(reimbId)");

		List<Reimbursement> reimbursement = new ArrayList<>();

		try (Connection con = JDBCUtil.getConnection()) {

			String getReimbStatusIdSQL = "SELECT reimbursement_id, reimbursement_submitted, "
					+ "reimbursement_resolved, reimbursement_status, reimbursement_type, "
					+ "reimbursement_description, reimbursement_amount, reimbursement_author, reimbursement_resolver "
					+ "FROM reimbursement " + "WHERE reimbursement_author = ?;";

			try (PreparedStatement pstmtGetReimbStatusId = con.prepareStatement(getReimbStatusIdSQL)) {
				pstmtGetReimbStatusId.setInt(1, rembAuthor);

				ResultSet rs = pstmtGetReimbStatusId.executeQuery();

				while (rs.next()) {

					reimbursement.add(new Reimbursement(rs.getInt("reimbursement_id"),
							rs.getString("reimbursement_submitted"), rs.getString("reimbursement_resolved"),
							rs.getString("reimbursement_status"), rs.getString("reimbursement_type"),
							rs.getString("reimbursement_description"), rs.getInt("reimbursement_amount"),
							rs.getInt("reimbursement_author"), rs.getInt("reimbursement_resolver")));
				}

				return reimbursement;
			}
		} catch (SQLException e) {
			throw new SQLException("Cannot complete the tasks at this time, please try again later");
		}
	}

	@Override
	public Reimbursement getFilteredStatus(String showStatus) throws SQLException {

		logger.info("getPendingRequest(int id)");

		try (Connection con = JDBCUtil.getConnection()) {

			String getFilteSQL = "SELECT * FROM reimbursement WHERE reimbursement_status = ? ORDER BY reimbursement_submitted;";

			try (PreparedStatement pstmtGetFilteredStatus = con.prepareStatement(getFilteSQL)) {
				pstmtGetFilteredStatus.setString(1, showStatus);

				ResultSet rs = pstmtGetFilteredStatus.executeQuery();

				if (rs.next()) {
					return new Reimbursement(rs.getInt("reimbursement_id"), rs.getString("reimbursement_submitted"),
							rs.getString("reimbursement_resolved"), rs.getString("reimbursement_status"),
							rs.getString("reimbursement_type"), rs.getString("reimbursement_description"),
							rs.getInt("reimbursement_amount"), rs.getInt("reimbursement_author"),
							rs.getInt("reimbursement_resolver"));
				} else {
					return null;
				}
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot filter out this particualar status, try again later!");
		}

	}

	@Override
	public List<Reimbursement> getEmployeeReimbPastHistory() throws SQLException {

		logger.info("getEmployeeReimbPastHistoy() invoked");

		List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();

		try (Connection con = JDBCUtil.getConnection()) {

			String getEmpReimbHistorySQL = "SELECT * FROM reimbursement ORDER BY reimbursement_submitted;";

			try (PreparedStatement pstmtGetEmpPastHist = con.prepareStatement(getEmpReimbHistorySQL)) {
				ResultSet rs = pstmtGetEmpPastHist.executeQuery();

				while (rs.next()) {

					reimbursements.add(new Reimbursement(rs.getInt("reimbursement_id"),
							rs.getString("reimbursement_submitted"), rs.getString("reimbursement_resolved"),
							rs.getString("reimbursement_status"), rs.getString("reimbursement_type"),
							rs.getString("reimbursement_description"), rs.getInt("reimbursement_amount"),
							rs.getInt("reimbursement_author"), rs.getInt("reimbursement_resolver")));
				}

				return reimbursements;
			}

		} catch (SQLException e) {
			throw new SQLException("No reimbursement is available at this time, please try again!");
		}
	}

	@Override
	public List<Reimbursement> getAllReimbursementByUserId(int resolver, int greaterThan, int lessThan)
			throws SQLException {

		logger.info("getAllReimbursementByUserId(int id...)");

		List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();

		try (Connection con = JDBCUtil.getConnection()) {

			String getAllReimbUserId = "SELECT * FROM reimbursement WHERE reimbursement_resolver = ? AND reimbursement_amount >= ? AND reimbursement_amount <= ?;";

			try (PreparedStatement pstmtGetReimbByUserId = con.prepareStatement(getAllReimbUserId)) {
				pstmtGetReimbByUserId.setInt(1, resolver);
				pstmtGetReimbByUserId.setInt(2, greaterThan);
				pstmtGetReimbByUserId.setInt(3, lessThan);

				ResultSet rs = pstmtGetReimbByUserId.executeQuery();

				while (rs.next()) {

					reimbursements.add(new Reimbursement(rs.getInt("reimbursement_id"),
							rs.getString("reimbursement_submitted"), rs.getString("reimbursement_resolved"),
							rs.getString("reimbursement_status"), rs.getString("reimbursement_type"),
							rs.getString("reimbursement_description"), rs.getInt("reimbursement_amount"),
							rs.getInt("reimbursement_author"), rs.getInt("reimbursement_resolver")));
				}

				return reimbursements;
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot complete the task at this time, please try again later!");
		}

	}

	@Override
	public List<Reimbursement> getAllReimbursementByReimbId(String pending, String approved, String rejected)
			throws SQLException {

		logger.info("getAllReimbursementByReimbId(int id...)");

		List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();

		try (Connection con = JDBCUtil.getConnection()) {

			String getAllReimburSQL = "SELECT * FROM reimbursement WHERE reimbursement_status = ?;";

			try (PreparedStatement pstmtGetAllReimbByReimbId = con.prepareStatement(getAllReimburSQL)) {
				pstmtGetAllReimbByReimbId.setString(1, pending);
				pstmtGetAllReimbByReimbId.setString(2, approved);
				pstmtGetAllReimbByReimbId.setString(3, rejected);

				ResultSet rs = pstmtGetAllReimbByReimbId.executeQuery();

				while (rs.next()) {

					reimbursements.add(new Reimbursement(rs.getInt("reimbursement_id"),
							rs.getString("reimbursement_submitted"), rs.getString("reimbursement_resolved"),
							rs.getString("reimbursement_status"), rs.getString("reimbursement_type"),
							rs.getString("reimbursement_description"), rs.getInt("reimbursement_amount"),
							rs.getInt("reimbursement_author"), rs.getInt("reimbursement_resolver")));
				}

				return reimbursements;
			}

		} catch (SQLException e) {
			throw new SQLException("Cannot complete the task at this time, please try again later!");
		}

	}

}
