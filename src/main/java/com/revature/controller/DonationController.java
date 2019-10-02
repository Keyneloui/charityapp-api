package com.revature.controller;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.dao.DonationDAO;
import com.revature.dao.DonationDAOImpl;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.exception.DBException;
import com.revature.model.DonationRequest;
import com.revature.model.DonorActivity;
import com.revature.services.DonationService;


public class DonationController {

	static DonationDAO dao = new DonationDAOImpl();
	static DonationService ds = new DonationService();

	public static String listRequest() {
		String json = null;
		List<DonationRequest> list = null;
		String errorMessage = null;
		try {
			
			list=ds.findAll();
			

		} catch (DBException e) {
			errorMessage = e.getMessage();

		}
		if (list != null) {
			Gson gson = new Gson();
			json = gson.toJson(list);
		}
		if (errorMessage != null) {
			JsonObject obj = new JsonObject();
			obj.addProperty("errorMessage", errorMessage);
		}
		System.out.println("List"+json);
		return json;

	}

	public static String addRequest(String requestType, int requestId, double requestAmount) {

		String errorMessage = null;
		String message = null;
		DonationRequest dr = null;
		
		try {
			dr = new DonationRequest();

			dr.setRequestType(requestType);
			dr.setRequestId(requestId);
			dr.setRequestAmount(requestAmount);
			ds.addDonations(dr);
			message = "Success";

		} catch (Exception e) {
		
			errorMessage = e.getMessage();
		}

		// Prepare JSON Object

		JsonObject obj = new JsonObject();
		if (message != null) {

			obj.addProperty("message", message);
		} else if (errorMessage != null) {
			obj.addProperty("errorMessage", errorMessage);
		}

		return obj.toString();

	}
	
	public static String validateRequestType(String requestType) throws DBException {
		
		DonationRequest donationRequest = dao.findByRequestType(requestType);
		if ( donationRequest == null) {
			throw new DBException("Request Type not found");
		}
		return requestType;
	}
	public static String updateRequest(String requestType, double requestAmount) {

		String errorMessage = null;
		String message = null;
		DonationRequest dr = null;
		DonationService ds = new DonationService();
		try {
			dr = new DonationRequest();

			dr.setRequestType(requestType);
		
			dr.setRequestAmount(requestAmount);
			
			validateRequestType(requestType);
			ds.updateDonationss(dr);
			message = "Success";

		} catch (Exception e) {
			
			errorMessage = e.getMessage();
		}

		// Prepare JSON Object

		JsonObject obj = new JsonObject();
		if (message != null) {

			obj.addProperty("message", message);
		} else if (errorMessage != null) {
			obj.addProperty("errorMessage", errorMessage);
		}

		return obj.toString();

	}



	public static String contributeRequest(double requestAmount, String requestType, int userId) {
		UserDAO udao = new UserDAOImpl();
		String errorMessage = null;
		String message = null;
		DonorActivity da = null;
		DonationService ds = new DonationService();

		da = new DonorActivity();
		da.setAmount(requestAmount);
		da.setRequestType(requestType);
		da.setId(userId);
		try {
			
			validateRequestType(requestType);
			ds.contributeDonation(da);
			message = "Success";
		} catch (DBException e) {
			errorMessage = e.getMessage();
		}
		JsonObject obj = new JsonObject();
		if (message != null) {

			obj.addProperty("message", message);
		} else if (errorMessage != null) {
			obj.addProperty("errorMessage", errorMessage);
		}

		return obj.toString();
	}

	public static void main(String[] args) {
		// testAddDonation();
		listRequest();
		//contributeToRequest();
		//updateRequest();
	}

	private static void testAddDonation() {
		System.out.println("Test Case 1: Valid Input");
		String validUserJson = DonationController.addRequest("Goods", 5, 10000);
		System.out.println(validUserJson);
		System.out.println("Test Case 2: Invalid Input");
		String invalidUserJson = DonationController.addRequest("Food", 1, 10000);

		System.out.println(invalidUserJson);

	}

	private static void contributeToRequest() {
		System.out.println("Test Case 1: Valid Input");
		String validUserJson = DonationController.contributeRequest(1000, "Food", 1);
		System.out.println(validUserJson);
		System.out.println("Test Case 2: Invalid Input");
		String invalidUserJson = DonationController.contributeRequest(10000, "Education", 1);
		System.out.println(invalidUserJson);

	}
	private static void updateRequest() {
		System.out.println("Test Case 1: Valid Input");
		String validUserJson = DonationController.updateRequest("Food",12000);
		System.out.println(validUserJson);
		System.out.println("Test Case 2: Invalid Input");
		String invalidUserJson = DonationController.updateRequest("education",12000);

		System.out.println(invalidUserJson);

	}

}
