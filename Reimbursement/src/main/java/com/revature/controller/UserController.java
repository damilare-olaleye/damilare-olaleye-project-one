package com.revature.controller;

import com.revature.dto.UserDTO;
import com.revature.model.User;
import com.revature.model.UserProfile;
import com.revature.service.AuthService;
import com.revature.service.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController implements Controller {

	private AuthService authorizationService;
	private UserService userService;

	public UserController() {
		this.authorizationService = new AuthService();
		this.userService = new UserService();
	}

	// Logged in as financial manager or employee
	private Handler getUserById = (ctx) -> {
		User user = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authorizationService.authorizeEmployeeAndFinanceManger(user);

	};

	// Logged in only as financial manager
	private Handler addFinanceManger = (ctx) -> {
		User user = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authorizationService.authorizeFinanceManager(user);

	};

	// get all users by username
	private Handler getAllUserbyUsername = (ctx) -> {

		UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);

		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authorizationService.authorizeEmployeeAndFinanceManger(currentlyLoggedInUser);

		UserProfile user = this.userService.displayUserbyUsername(userDTO.getUsername());

		if (user == null) {
			ctx.json("Cannot find username");
			ctx.status(500);
		}

		ctx.json(user);
		ctx.status(200);
	};

	@Override
	public void mapEndPoints(Javalin app) {
		app.get("/users/{userID}", getUserById);
		app.post("/users", addFinanceManger);
		app.get("/userByUsername", getAllUserbyUsername);

	}

}
