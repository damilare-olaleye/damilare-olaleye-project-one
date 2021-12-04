package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoginDTO;
import com.revature.dto.MessageDTO;
import com.revature.dto.SignupDTO;
import com.revature.model.User;
import com.revature.service.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AuthController implements Controller {

	private UserService userService;

	public AuthController() {
		this.userService = new UserService();
	}

	private Handler signup = (ctx) -> {

		SignupDTO signUpDto = ctx.bodyAsClass(SignupDTO.class);

	    User user = this.userService.userSignup(signUpDto.getUsername(), signUpDto.getPassword(),
				signUpDto.getFirstname(), signUpDto.getLastname(), signUpDto.getEmail(), signUpDto.getRole());

		ctx.json(user);
		ctx.status(201);
	};

	private Handler login = (ctx) -> {

		LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);

		User user = this.userService.userLogin(loginDTO.getUsername(), loginDTO.getPassword());

		HttpServletRequest req = ctx.req;

		HttpSession session = req.getSession();
		session.setAttribute("currentuser", user);

		ctx.json(user);
		ctx.status(200);

	};

	private Handler logout = (ctx) -> {
		HttpServletRequest req = ctx.req;

		req.getSession().invalidate();

		ctx.json("Successfully logged out");
		ctx.status(200);
	};

	private Handler checkIfLoggedIn = (ctx) -> {
		HttpSession session = ctx.req.getSession();

		if (!(session.getAttribute("currentuser") == null)) {
			ctx.json(session.getAttribute("currentuser"));
			ctx.status(200);
		} else {
			ctx.json(new MessageDTO("User is not logged in"));
			ctx.status(401);
		}
	};

	private Handler deleteAccount = (ctx) -> {
		HttpSession session = ctx.req.getSession();

		LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);

		if (!(session.getAttribute("currentuser") == null)) {
			this.userService.deleteUser(loginDTO.getUsername(), loginDTO.getPassword());
			
			ctx.json("Successfully delete account");
			ctx.status(200);
		} else {

			ctx.json("Cannot delete user, user not found");
			ctx.status(500);
		}

	};

	@Override
	public void mapEndPoints(Javalin app) {

		app.post("/signup", signup); // works
		app.post("/login", login); // works
		app.post("/logout", logout); // works
		app.get("/checkloginstatus", checkIfLoggedIn); // works
		app.delete("/deleteaccount", deleteAccount); // works but not neccessary

	}

}
