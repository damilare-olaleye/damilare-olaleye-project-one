package com.revature.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.security.auth.login.FailedLoginException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.UserDAO;
import com.revature.exceptions.FailedAuthenticationException;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.NotFoundException;
import com.revature.model.User;
import com.revature.model.UserProfile;

public class UserServiceTest {

	private UserService us;

	// POSITVE

	@Test
	public void testUserLoginPositive()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, FailedLoginException {
		UserDAO mockUserDao = mock(UserDAO.class);

		when(mockUserDao.userLogin(eq("lmessi"), eq("disIsMyPassword"))).thenReturn(
				new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT
		 * 
		 */

		User actual = userService.userLogin("lmessi", "disIsMyPassword");

		/*
		 * ASSERT
		 */

		User expected = userService.userLogin("lmessi", "disIsMyPassword");

		Assertions.assertEquals(expected, actual);
	}

	// NEGATIVE

	@Test
	public void testUserLoginFailedLoginException()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, FailedLoginException {

		/*
		 * ARRANGE
		 */
		UserDAO mockUserDao = mock(UserDAO.class);

		when(mockUserDao.userLogin(eq("lmessi"), eq("disIsMyPassword"))).thenReturn(
				new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 * 
		 */

		Assertions.assertThrows(FailedLoginException.class, () -> {

			userService.userLogin("12323", "88s");
		});

	}

	@Test
	public void testUserLoginFailedLoginInvalidParameterNegativeUsername()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, FailedLoginException {

		/*
		 * ARRANGE
		 */
		UserDAO mockUserDao = mock(UserDAO.class);

		when(mockUserDao.userLogin(eq("lmessi"), eq("disIsMyPassword"))).thenReturn(
				new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 * 
		 */

		Assertions.assertThrows(FailedLoginException.class, () -> {

			userService.userLogin("", "88s");
		});

	}

	@Test
	public void testUserLoginFailedLoginInvalidParameterNegativeBothBlankInputs()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, FailedLoginException {

		/*
		 * ARRANGE
		 */
		UserDAO mockUserDao = mock(UserDAO.class);

		when(mockUserDao.userLogin(eq("lmessi"), eq("disIsMyPassword"))).thenReturn(
				new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 * 
		 */

		Assertions.assertThrows(FailedLoginException.class, () -> {

			userService.userLogin(" ", " ");
		});

	}

	@Test
	public void testUserLoginFailedLoginNotFoundException()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, FailedLoginException {

		/*
		 * ARRANGE
		 */
		UserDAO mockUserDao = mock(UserDAO.class);

		when(mockUserDao.userLogin(eq("lmessi"), eq("disIsMyPassword"))).thenReturn(
				new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 * 
		 */

		Assertions.assertThrows(FailedLoginException.class, () -> {

			userService.userLogin("$$$", "&&&");
		});

	}

	@Test
	public void testUserSignupPositive() throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException,
			FailedLoginException, InvalidParameterException, NotFoundException, FailedAuthenticationException {
		UserDAO mockUserDao = mock(UserDAO.class);

		when(mockUserDao.userSignup(eq("lmessi"), eq("disIsMyPassword13"), eq("Lionnel"), eq("Messi"),
				eq("lmessi@yahoo.com"), eq("Finance Manager")))
						.thenReturn(new User(1, "lmessi", "disIsMyPassword13", "Lionnel", "Messi", "lmessi@yahoo.com",
								"Finance Manager"));

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT
		 * 
		 */

		User actual = userService.userSignup("lmessi", "disIsMyPassword13", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		/*
		 * ASSERT
		 */

		User expected = userService.userSignup("lmessi", "disIsMyPassword13", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testUserSignupInvalidParameterException() throws NoSuchAlgorithmException, InvalidKeySpecException,
			SQLException, InvalidParameterException, NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			userService.userSignup("lmessi", "disIsMyPasswo", "Lionnel", "Messi", "lmessi@yahoo.com",
					"Finance Manager");
		});

	}

	@Test
	public void testUserSignupInvalidParameterExceptionEverythingBlank()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, InvalidParameterException,
			NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			userService.userSignup("", "", "", "", "", " ");
		});

	}

	@Test
	public void testUserSignupInvalidParameterExceptionEverythingElseValid()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, InvalidParameterException,
			NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			userService.userSignup("lmessi", "` ", "Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager");
		});

	}

	@Test
	public void testUserSignupInvalidParameterExceptionUsernamePassword()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, InvalidParameterException,
			NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			userService.userSignup("'", "` ", "Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager");
		});

	}

	@Test
	public void testUserSignupInvalidParameterExceptionUsernamePasswordFirstName()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, InvalidParameterException,
			NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			userService.userSignup("'", "` ", "#", "Messi", "lmessi@yahoo.com", "Finance Manager");
		});

	}

	@Test
	public void testUserSignupInvalidParameterExceptionFirstNameLastName()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, InvalidParameterException,
			NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			userService.userSignup("lmessi", "disIsMyPassword13", "`", "!", "lmessi@yahoo.com", "Finance Manager");
		});

	}

	@Test
	public void testUserSignupInvalidParameterExceptionLastNameEmail()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, InvalidParameterException,
			NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			userService.userSignup("lmessi", "disIsMyPassword13", "Lionnel", "`", "@yahoo.com", "Finance Manager");
		});

	}

	@Test
	public void testUserSignupInvalidParameterExceptionOnlyEmail()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, InvalidParameterException,
			NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			userService.userSignup("lmessi", "disIsMyPassword13", "Lionnel", "Messi", "lmessi.com", "Finance Manager");

		});

	}

	@Test
	public void testUserSignupInvalidParameterExceptionWrongRole()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, InvalidParameterException,
			NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			userService.userSignup("lmessi", "disIsMyPassword13", "Lionnel", "Messi", "lmessi@yahoo.com", "Other");

		});

	}

	@Test
	public void testUserSignupNotFoundException() throws NoSuchAlgorithmException, InvalidKeySpecException,
			SQLException, InvalidParameterException, NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(NotFoundException.class, () -> {

			userService.userSignup("lmessi", " ", "Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager");
		});

	}

	@Test
	public void testUserSignupNotFoundExceptionBlankEverythingElse()
			throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, InvalidParameterException,
			NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(NotFoundException.class, () -> {

			userService.userSignup(" ", "             ", "Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager");
		});

	}

	@Test
	public void testUserSignupNotFoundExceptionWrongRole() throws NoSuchAlgorithmException, InvalidKeySpecException,
			SQLException, InvalidParameterException, NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			userService.userSignup("lmessi", "disIsMyPassword13", "Lionnel", "Messi", "lmessi@yahoo.com", "Other");
		});

	}

	@Test
	public void testUserSignupNotFoundExceptionRoleBlank() throws NoSuchAlgorithmException, InvalidKeySpecException,
			SQLException, InvalidParameterException, NotFoundException, FailedAuthenticationException {

		/*
		 * ARRANGE
		 */

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		/*
		 * ACT AND ASSERT
		 */

		Assertions.assertThrows(InvalidParameterException.class, () -> {

			userService.userSignup("lmessi", "disIsMyPassword13", "Lionnel", "Messi", "lmessi@yahoo.com", " ");
		});

	}

	@Test
	public void testPostiveDisplayUsername() throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException,
			NotFoundException, InvalidParameterException {

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		when(mockUserDao.getAllUserbyUsername(eq("lmessi")))
				.thenReturn(new UserProfile("Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		/*
		 * ACT
		 * 
		 */

		UserProfile actual = userService.displayUserbyUsername("lmessi");

		/*
		 * ASSERT
		 */

		Assertions.assertEquals(new UserProfile("Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"), actual);

	}

	@Test
	public void testNegativeDisplayUsernameNotFoundNegative() throws NoSuchAlgorithmException, InvalidKeySpecException,
			SQLException, NotFoundException, InvalidParameterException {

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		when(mockUserDao.getAllUserbyUsername(eq("lmessi")))
				.thenReturn(new UserProfile("Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		/*
		 * ACT AND ASSERT
		 * 
		 */

		Assertions.assertThrows(NotFoundException.class, () -> {

			userService.displayUserbyUsername("1");
		});

	}

	@Test
	public void testNegativeDisplayUsernameBySpacing() throws NoSuchAlgorithmException, InvalidKeySpecException,
			SQLException, NotFoundException, InvalidParameterException {

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		when(mockUserDao.getAllUserbyUsername(eq("lmessi")))
				.thenReturn(new UserProfile("Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		/*
		 * ACT AND ASSERT
		 * 
		 */

		Assertions.assertThrows(NotFoundException.class, () -> {

			userService.displayUserbyUsername(" ");
		});

	}

	@Test
	public void testNegativeDisplayUsernameByrandoStrings() throws NoSuchAlgorithmException, InvalidKeySpecException,
			SQLException, NotFoundException, InvalidParameterException {

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		when(mockUserDao.getAllUserbyUsername(eq("lmessi")))
				.thenReturn(new UserProfile("Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		/*
		 * ACT AND ASSERT
		 * 
		 */

		Assertions.assertThrows(NotFoundException.class, () -> {

			userService.displayUserbyUsername(" c382 92 wwe");
		});

	}

	@Test
	public void testPositiveDisplayUserProfile() throws SQLException, NotFoundException, InvalidParameterException {

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		User currentlyLoggedInUser = new User(1, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		when(mockUserDao.showUserProfile(eq(1)))
				.thenReturn(new UserProfile("Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		/*
		 * ACT
		 * 
		 */

		UserProfile actual = userService.displayUserProfile(currentlyLoggedInUser);

		/*
		 * ASSERT
		 */

		Assertions.assertEquals(new UserProfile("Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"), actual);

	}

	@Test
	public void testNegativeDisplayUserProfile() throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException,
			NotFoundException, InvalidParameterException {

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		User currentlyLoggedInUser = new User(5, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		when(mockUserDao.showUserProfile(eq(1)))
				.thenReturn(new UserProfile("Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		/*
		 * ACT AND ASSERT
		 * 
		 */

		Assertions.assertThrows(NotFoundException.class, () -> {

			userService.displayUserProfile(currentlyLoggedInUser);
		});

	}

	@Test
	public void testNegativeDisplayUserProfileNegativeNumber() throws NoSuchAlgorithmException, InvalidKeySpecException,
			SQLException, NotFoundException, InvalidParameterException {

		UserDAO mockUserDao = mock(UserDAO.class);

		UserService userService = new UserService(mockUserDao);

		User currentlyLoggedInUser = new User(-5, "lmessi", "disIsMyPassword", "Lionnel", "Messi", "lmessi@yahoo.com",
				"Finance Manager");

		when(mockUserDao.showUserProfile(eq(1)))
				.thenReturn(new UserProfile("Lionnel", "Messi", "lmessi@yahoo.com", "Finance Manager"));

		/*
		 * ACT AND ASSERT
		 * 
		 */

		Assertions.assertThrows(NotFoundException.class, () -> {

			userService.displayUserProfile(currentlyLoggedInUser);
		});

	}

}
