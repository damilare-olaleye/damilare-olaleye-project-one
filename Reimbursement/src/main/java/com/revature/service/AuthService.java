package com.revature.service;

import com.revature.exceptions.UnauthorizedException;
import com.revature.model.User;

public class AuthService {

	public void authorizeEmployeeAndFinanceManger(User user) throws UnauthorizedException {

		if (user == null
				|| !(user.getRole().equals("Employee") || user.getRole().equals("Finance Manager"))) {
			throw new UnauthorizedException("Sign in as an Employee or Finance Manager to access this resources");
		}
	}

	public void authorizeFinanceManager(User user) throws UnauthorizedException {
		if (user == null || !user.getRole().equals("Finance Manager")) {
			throw new UnauthorizedException("Sign in as a Finance Manager to access this resources");
		}
	}

	public void authorizeEmployee(User user) throws UnauthorizedException {
		if (user == null || !user.getRole().equals("Employee")) {
			throw new UnauthorizedException("You must be logged in and have a role of employee for this resource");
		}

	}
}
