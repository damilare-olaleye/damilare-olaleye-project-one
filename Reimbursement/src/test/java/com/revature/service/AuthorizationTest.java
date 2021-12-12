package com.revature.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.exceptions.UnauthorizedException;
import com.revature.model.User;

public class AuthorizationTest {

	private AuthService authService;

	@BeforeEach
	public void setup() {
		this.authService = new AuthService();
	}

	@Test
	public void authorizeFinanceManagerPositive() throws UnauthorizedException {
		User user = new User(1, "lMessi", "disIsMyPassword13", "Lionnel", "Messi", "lMessi@psg.com", "Finance Manager");

		this.authService.authorizeFinanceManager(user);
	}

	@Test
	public void authorizeFinanceMangerIsNullNegativeTest() {
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeFinanceManager(null);
		});
	}

	@Test
	public void authorizeFinanceManagerNegativeTestRequiredFinanceManager() throws UnauthorizedException {
		User user = new User(2, "lMessi", "disIsMyPassword13", "dsfda", "Ssdfam", "lMessi@gmail.com", "Employee");

		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeFinanceManager(user);
		});
	}

	@Test
	public void authorizeEmployeePositive() throws UnauthorizedException {
		User user = new User(1, "lMessi", "disIsMyPassword13", "Lionnel", "Messi", "lMessi@psg.com", "Employee");

		this.authService.authorizeEmployee(user);
	}

	@Test
	public void authorizeEmployeeIsNullNegativeTest() {
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployee(null);
		});
	}

	@Test
	public void authorizeEmployeeAndFinanceManagerpositiveTestuserIsEmployee() throws UnauthorizedException {
		User user = new User(1, "lMessi", "disIsMyPassword13", "Lionnel", "Messi", "lMessi@psg.com", "Nill");

		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployeeAndFinanceManger(user);
		});
	}

	@Test
	public void authorizedEmployeeAndFinanceManagerNegativeUserNull() {
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployeeAndFinanceManger(null);
		});
	}

	@Test
	public void authorizedEmployeeAndFinanceManagerPositiveTestWhenUserIsEmployee() throws UnauthorizedException {
		User user = new User(1, "lMessi", "disIsMyPassword13", "Lionnel", "Messi", "lMessi@psg.com", "Employee");

		this.authService.authorizeEmployeeAndFinanceManger(user);
	}

	@Test
	public void authorizedEmployeeAndFinanceManagerPositiveTestWhenUserIsFinanceManager() throws UnauthorizedException {
		User user = new User(1, "lMessi", "disIsMyPassword13", "Lionnel", "Messi", "lMessi@psg.com", "Finance Manager");
		this.authService.authorizeEmployeeAndFinanceManger(user);
	}

}
