package com.revature.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.ReimbursementDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.NotFoundException;
import com.revature.exceptions.UnauthorizedException;
import com.revature.exceptions.reimbursementAlreadyResloved;
import com.revature.model.Reimbursement;
import com.revature.model.User;

public class ReimbursementServiceTest {

	private ReimbursementService reimbService;

	@Test
	public void testGetAllReimbursementPositve()
			throws SQLException, InvalidParameterException, UnauthorizedException, NotFoundException {

		/*
		 * ARRANGE
		 */
		ReimbursementDAO mockreimbursementDao = mock(ReimbursementDAO.class);
		UserDAO mockUserDao = mock(UserDAO.class);

		User currentlyLoggedInUser = new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		Reimbursement reimbursement1 = new Reimbursement(2, "12-12-2021", "12-12-2021", "pending", "other",
				"with co workers", 21.9, 1, 1);
		Reimbursement reimbursement2 = new Reimbursement(4, "12-12-2021", "12-12-2021", "approved", "other",
				"with co workers", 21.9, 1, 1);
		Reimbursement reimbursement3 = new Reimbursement(3, "12-12-2021", "12-12-2021", "approved", "other",
				"with co workers", 21.9, 1, 1);

		List<Reimbursement> reimbursementsFromDao = new ArrayList<>();
		reimbursementsFromDao.add(reimbursement1);
		reimbursementsFromDao.add(reimbursement2);
		reimbursementsFromDao.add(reimbursement3);

		when(mockreimbursementDao.getAllReimbursements()).thenReturn(reimbursementsFromDao);

		ReimbursementService reimbursementService = new ReimbursementService(mockreimbursementDao, mockUserDao);

		/*
		 * ACT
		 */

		List<Reimbursement> actual = reimbursementService.getAllReimbursement(currentlyLoggedInUser);

		/*
		 * ASSERT
		 */
		List<Reimbursement> expected = new ArrayList<>();
		expected.add(
				new Reimbursement(2, "12-12-2021", "12-12-2021", "pending", "other", "with co workers", 21.9, 1, 1));

		expected.add(
				new Reimbursement(4, "12-12-2021", "12-12-2021", "approved", "other", "with co workers", 21.9, 1, 1));

		expected.add(
				new Reimbursement(3, "12-12-2021", "12-12-2021", "approved", "other", "with co workers", 21.9, 1, 1));

		Assertions.assertEquals(expected, actual);

	}

	@Test
	public void testGetAllReimbursementSQLExceptionNegative() throws SQLException {

		// ARRANGE

		UserDAO mockUserDao = mock(UserDAO.class);

		User currentlyLoggedInUser = new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);

		when(mockReimbursementDao.getAllReimbursements()).thenThrow(SQLException.class);

		ReimbursementService reimbursementService = new ReimbursementService(mockReimbursementDao, mockUserDao);

		// ACT AND ASSERT

		Assertions.assertThrows(SQLException.class, () -> {

			reimbursementService.getAllReimbursement(currentlyLoggedInUser);

		});

	}

	@Test
	public void testGetAllReimbursementNumberFormatException() throws NumberFormatException, SQLException {

		// ARRANGE

		UserDAO mockUserDao = mock(UserDAO.class);

		User currentlyLoggedInUser = new User(-20, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);

		when(mockReimbursementDao.getAllReimbursements()).thenThrow(NumberFormatException.class);

		ReimbursementService reimbursementService = new ReimbursementService(mockReimbursementDao, mockUserDao);

		// ACT AND ASSERT

		Assertions.assertThrows(NumberFormatException.class, () -> {

			reimbursementService.getAllReimbursement(currentlyLoggedInUser);

		});

	}

	@Test
	public void testGetAllReimbursementNegativeInvalidParameterException() throws SQLException {

		UserDAO mockUserDao = mock(UserDAO.class);

		User currentlyLoggedInUser = new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);

		when(mockReimbursementDao.getAllReimbursements()).thenThrow(NumberFormatException.class);

		ReimbursementService reimbursementService = new ReimbursementService(mockReimbursementDao, mockUserDao);

		// ACT AND ASSERT

		Assertions.assertThrows(NumberFormatException.class, () -> {

			reimbursementService.getAllReimbursement(currentlyLoggedInUser);

		});

	}

	@Test
	public void testUpdateReimbursementPositve() throws SQLException, InvalidParameterException, UnauthorizedException,
			NotFoundException, reimbursementAlreadyResloved {

		/*
		 * ARRANGE
		 */
		ReimbursementDAO mockreimbursementDao = mock(ReimbursementDAO.class);
		UserDAO mockUserDao = mock(UserDAO.class);

		User currentlyLoggedInUser = new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		when(mockreimbursementDao.getReimbursementById(eq(3))).thenReturn(
				new Reimbursement(3, "12-12-2021", "12-12-2021", "approved", "other", "with co workers", 21.9, 1, 0));

		Reimbursement reimbursement1 = new Reimbursement(3, "12-12-2021", "12-12-2021", "PENDING", "other",
				"with co workers", 21.9, 1, 0);

		Reimbursement reimbursement2 = new Reimbursement(3, "12-12-2021", "12-12-2021", "PENDING", "other",
				"with co workers", 21.9, 1, 0);

		Reimbursement reimbursement3 = new Reimbursement(3, "12-12-2021", "12-12-2021", "PENDING", "other",
				"with co workers", 21.9, 1, 0);

		List<Reimbursement> reimbursementsFromDao = new ArrayList<>();
		reimbursementsFromDao.add(reimbursement1);
		reimbursementsFromDao.add(reimbursement2);
		reimbursementsFromDao.add(reimbursement3);

		when(mockreimbursementDao.getAllReimbursements()).thenReturn(reimbursementsFromDao);

		ReimbursementService reimbursementService = new ReimbursementService(mockreimbursementDao, mockUserDao);

		/*
		 * ACT
		 */

		Reimbursement actual = reimbursementService.updateReimbursement(currentlyLoggedInUser, "3", "PENDING");

		/*
		 * ASSERT
		 */
		Reimbursement expected = reimbursementService.updateReimbursement(currentlyLoggedInUser, "3", "PENDING");

		Assertions.assertEquals(expected, actual);

	}

	@Test
	public void testUpdateReimbursementSQLExceptionNegative() throws SQLException {

		UserDAO mockUserDao = mock(UserDAO.class);

		User currentlyLoggedInUser = new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);

		when(mockReimbursementDao.getAllReimbursements()).thenThrow(SQLException.class);

		ReimbursementService reimbursementService = new ReimbursementService(mockReimbursementDao, mockUserDao);

		// ACT AND ASSERT

		Assertions.assertThrows(NotFoundException.class, () -> {

			reimbursementService.updateReimbursement(currentlyLoggedInUser, "0", "other");
		});
	}

	@Test
	public void testUpdateReimbursementInvalidParameterNegative() throws SQLException {

		UserDAO mockUserDao = mock(UserDAO.class);

		User currentlyLoggedInUser = new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);

		when(mockReimbursementDao.getAllReimbursements()).thenThrow(SQLException.class);

		ReimbursementService reimbursementService = new ReimbursementService(mockReimbursementDao, mockUserDao);

		// ACT AND ASSERT

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			reimbursementService.updateReimbursement(currentlyLoggedInUser, "abs", "APPROVED");
		});
	}

	@Test
	public void testGetReimbursementByStatusPositive()
			throws SQLException, InvalidParameterException, UnauthorizedException, NotFoundException {

		// ARRANGE

		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);

		UserDAO mockUserDao = mock(UserDAO.class);

		User currentlyLoggedInUser = new User(3, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		Reimbursement reimbursement1 = new Reimbursement(3, "12-12-2021", "12-12-2021", "PENDING", "other",
				"with co workers", 21.9, 1, 0);

		List<Reimbursement> reimbursementsFromDao = new ArrayList<>();
		reimbursementsFromDao.add(reimbursement1);

		when(mockReimbursementDao.getReimbursementStatusById(eq(3))).thenReturn(reimbursementsFromDao);

		ReimbursementService reimbursementService = new ReimbursementService(mockReimbursementDao, mockUserDao);

		// ACT

		List<Reimbursement> actual = reimbursementService.getReimbursementStatus(currentlyLoggedInUser);

		// ASSERT

		Assertions.assertEquals(reimbursementsFromDao, actual);
	}

	@Test
	public void testGetPendingRequestByIdPositive()
			throws SQLException, InvalidParameterException, UnauthorizedException, NotFoundException {

		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);

		Reimbursement reimbursement1 = new Reimbursement(3, "12-12-2021", "12-12-2021", "PENDING", "other",
				"with co workers", 21.9, 1, 0);

		List<Reimbursement> reimbursementsFromDao = new ArrayList<>();
		reimbursementsFromDao.add(reimbursement1);

		when(mockReimbursementDao.getReimbursementStatusById(eq(3))).thenReturn(reimbursementsFromDao);

		// ACT

		Reimbursement reimbursement2 = new Reimbursement(3, "12-12-2021", "12-12-2021", "PENDING", "other",
				"with co workers", 21.9, 1, 0);

		List<Reimbursement> actualReimb = new ArrayList<>();
		actualReimb.add(reimbursement2);

		// ASSERT

		Assertions.assertEquals(reimbursementsFromDao, actualReimb);
	}

	@Test
	public void testGetAllEmployeeReimbPastHistoryPositiveTest()
			throws SQLException, InvalidParameterException, UnauthorizedException, NotFoundException {

		/*
		 * ARRANGE
		 */

		ReimbursementDAO mockreimbursementDao = mock(ReimbursementDAO.class);
		UserDAO mockUserDao = mock(UserDAO.class);

		User currentlyLoggedInUser = new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		Reimbursement reimbursement1 = new Reimbursement(2, "12-12-2021", "12-12-2021", "pending", "other",
				"with co workers", 21.9, 1, 1);
		Reimbursement reimbursement2 = new Reimbursement(4, "12-12-2021", "12-12-2021", "approved", "other",
				"with co workers", 21.9, 1, 1);
		Reimbursement reimbursement3 = new Reimbursement(3, "12-12-2021", "12-12-2021", "approved", "other",
				"with co workers", 21.9, 1, 1);

		List<Reimbursement> reimbursementsFromDao = new ArrayList<>();
		reimbursementsFromDao.add(reimbursement1);
		reimbursementsFromDao.add(reimbursement2);
		reimbursementsFromDao.add(reimbursement3);

		when(mockreimbursementDao.getAllReimbursements()).thenReturn(reimbursementsFromDao);

		ReimbursementService reimbursementService = new ReimbursementService(mockreimbursementDao, mockUserDao);

		/*
		 * ACT
		 */

		List<Reimbursement> actual = reimbursementService.getAllReimbursement(currentlyLoggedInUser);

		/*
		 * ASSERT
		 */
		List<Reimbursement> expected = new ArrayList<>();
		expected.add(
				new Reimbursement(2, "12-12-2021", "12-12-2021", "pending", "other", "with co workers", 21.9, 1, 1));

		expected.add(
				new Reimbursement(4, "12-12-2021", "12-12-2021", "approved", "other", "with co workers", 21.9, 1, 1));

		expected.add(
				new Reimbursement(3, "12-12-2021", "12-12-2021", "approved", "other", "with co workers", 21.9, 1, 1));

		Assertions.assertEquals(expected, actual);

	}

	@Test
	public void testGetAllEmployeeReimbPastHistorySQLExceptionNegativeTest() throws SQLException {

		// ARRANGE

		UserDAO mockUserDao = mock(UserDAO.class);

		User currentlyLoggedInUser = new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);

		when(mockReimbursementDao.getAllReimbursements()).thenThrow(SQLException.class);

		ReimbursementService reimbursementService = new ReimbursementService(mockReimbursementDao, mockUserDao);

		// ACT AND ASSERT

		Assertions.assertThrows(SQLException.class, () -> {

			reimbursementService.getAllReimbursement(currentlyLoggedInUser);

		});

	}

	@Test
	public void testGetEmployeePastHistoryNumberFormatException() throws NumberFormatException, SQLException {

		// ARRANGE

		UserDAO mockUserDao = mock(UserDAO.class);

		User currentlyLoggedInUser = new User(-20, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);

		when(mockReimbursementDao.getAllReimbursements()).thenThrow(NumberFormatException.class);

		ReimbursementService reimbursementService = new ReimbursementService(mockReimbursementDao, mockUserDao);

		// ACT AND ASSERT

		Assertions.assertThrows(NumberFormatException.class, () -> {

			reimbursementService.getAllReimbursement(currentlyLoggedInUser);

		});

	}

}
