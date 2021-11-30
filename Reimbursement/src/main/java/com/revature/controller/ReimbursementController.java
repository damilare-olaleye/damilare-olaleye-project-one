package com.revature.controller;

import java.io.InputStream;
import java.util.List;

import org.apache.tika.Tika;

import com.revature.dao.ReimbursementDAO;
import com.revature.dto.MessageDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.AuthService;
import com.revature.service.ReimbursementService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;

public class ReimbursementController implements Controller {

	private AuthService authorizationService;
	private ReimbursementService reimbursementService;

	ReimbursementDAO remibursementDao = new ReimbursementDAO();

	public ReimbursementController() {
		this.authorizationService = new AuthService();
		this.reimbursementService = new ReimbursementService();
	}

	private Handler submitRequest = (ctx) -> {

		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authorizationService.authorizeEmployee(currentlyLoggedInUser);

		String reimbursementType = ctx.formParam("type");
		String amount = ctx.formParam("amount");
		String description = ctx.formParam("description");
		UploadedFile file = ctx.uploadedFile("receipt");
		
		if (file == null) {
			ctx.status(400);
			ctx.json(new MessageDTO("Must have an image to upload"));
			return;
		}

		if (amount == null) {
			ctx.status(400);
			ctx.json(new MessageDTO("Must input amount"));
			return;
		}

		if (reimbursementType == null) {
			ctx.status(400);
			ctx.json(new MessageDTO("Must input reimbursement type"));
			return;
		}

		if (description == null) {
			ctx.status(400);
			ctx.json(new MessageDTO("Must input description"));
			return;
		}
		
		InputStream content = file.getContent();
		
		Tika tika = new Tika();
		String mimetype = tika.detect(content);

		Reimbursement addedReimbursement = this.reimbursementService.submitReimbursementRequest(currentlyLoggedInUser,
				reimbursementType, description, amount, mimetype, content);

		ctx.json(addedReimbursement);
		ctx.status(201);
	};

	private Handler viewAllReimbursements = (ctx) -> {

		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authorizationService.authorizeFinanceManager(currentlyLoggedInUser);

		List<Reimbursement> reimbursements = this.reimbursementService.getAllReimbursement(currentlyLoggedInUser);

		if (reimbursements.isEmpty()) {
			ctx.json("Reimbursements is currently null, try again later");
			ctx.status(404);
		}

		ctx.json(reimbursements);
		ctx.status(201);
	};

	private Handler updateReimbursement = (ctx) -> {

		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authorizationService.authorizeFinanceManager(currentlyLoggedInUser);

		String reimbursementId = ctx.pathParam("id");
		String status = ctx.formParam("status");

		Reimbursement reimbursement = this.reimbursementService.updateReimbursement(currentlyLoggedInUser,
				reimbursementId, status);

		ctx.json(reimbursement);
		ctx.status(200);

	};

	private Handler viewPastTickets = (ctx) -> {

		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authorizationService.authorizeEmployee(currentlyLoggedInUser);

		String reimbursementId = ctx.pathParam("id");

		InputStream image = this.reimbursementService.getImageFromReimbursementById(currentlyLoggedInUser,
				reimbursementId);

		Tika tika = new Tika();
		String mimeType = tika.detect(image);

		ctx.contentType(mimeType);
		ctx.result(image);

	};

	private Handler viewPendingReimbursements = (ctx) -> {

		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authorizationService.authorizeEmployee(currentlyLoggedInUser);

		String reimbursementId = ctx.pathParam("id");
		String reimbursementStatus = ctx.formParam("status");

		Reimbursement reimbursement = this.reimbursementService.getPendingRequestById(currentlyLoggedInUser,
				reimbursementId, reimbursementStatus);

		ctx.json(reimbursement);
		ctx.status(200);

	};

	@Override
	public void mapEndPoints(Javalin app) {

		// EMPLOYEE
		app.post("/submitRequest", submitRequest);
		app.get("/reimbursement/{id}/reciept", viewPastTickets);
		app.get("/pendingReimbursements/{id}", viewPendingReimbursements);

		// FINANCE MANAGER
		app.get("/allreimbursements", viewAllReimbursements);
		app.patch("/reimbursement/{id}/status", updateReimbursement);
	}

}
