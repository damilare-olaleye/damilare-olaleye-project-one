package com.revature.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.AuthController;
import com.revature.controller.Controller;
import com.revature.controller.ReimbursementController;
import com.revature.controller.UserController;
import com.revature.exceptions.ExceptionMapper;

import io.javalin.Javalin;

public class Main {

	public static void main(String[] args) {

		Javalin app = Javalin.create((config) -> {

			config.enableCorsForOrigin("http://localhost:5500", "http://127.0.0.1:5500");

			// config.addStaticFiles("static", Location.CLASSPATH);
		});

		mapControllers(app, new AuthController(), new UserController(), new ReimbursementController());

		ExceptionMapper mapper = new ExceptionMapper();
		mapper.mapExceptions(app);

		app.start(8080);

		Logger logger = LoggerFactory.getLogger(Main.class);

		app.before(ctx -> {
			logger.info(ctx.method() + "request received to the " + ctx.path() + " endpoint");
		});

	}

	private static void mapControllers(Javalin app, Controller... controllers) {

		for (int i = 0; i < controllers.length; i++) {
			controllers[i].mapEndPoints(app);
		}

	}

}
