package com.revature.controller;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.dao.DonationDAO;
import com.revature.dao.DonationDAOImpl;


import com.revature.exception.ServiceException;
import com.revature.model.DonationRequest;
import com.revature.model.DonorActivity;
import com.revature.services.DonationService;

public class DonationController {

	private static final String REQUEST_TYPE_EXISTS = "Request Type Exists";
	private static final String MESSAGE2 = "message";
	private static final String TEST_CASE_2_INVALID_INPUT = "Test Case 2: Invalid Input";
	private static final String TEST_CASE_1_VALID_INPUT = "Test Case 1: Valid Input";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String SUCCESS = "Success";
	
	static DonationDAO donationDao = new DonationDAOImpl();
	static DonationService donationService = new DonationService();
	
	/**
	 * method to call the donation service for listing the donation request
	 * 
	 **/

	public static String listRequest() {
		String json = null;
		List<DonationRequest> list = null;
		String errorMessage = null;
		try {

			list = donationService.findAll();

		} catch (ServiceException e) {
			errorMessage = e.getMessage();

		}
		if (list != null) {
			Gson gson = new Gson();
			json = gson.toJson(list);
		}
		if (errorMessage != null) {
			JsonObject obj = new JsonObject();
			obj.addProperty(ERROR_MESSAGE, errorMessage);
		}

		return json;

	}
	/**
	 * method to call the donation service for adding the donation request
	 * 
	 **/

	public static String addRequest(String requestType, double requestAmount) {

		String errorMessage = null;
		String message = null;
		DonationRequest donationRequest = null;

		try {
			donationRequest = new DonationRequest();

			donationRequest.setRequestType(requestType);
			validateRequestType(requestType);

			donationRequest.setRequestAmount(requestAmount);
			donationService.addDonations(donationRequest);
			message = SUCCESS;

		} catch (ServiceException e) {

			errorMessage = e.getMessage();
		}

		// Prepare JSON Object

		JsonObject obj = new JsonObject();
		if (message != null) {

			obj.addProperty(MESSAGE2, message);
		} else if (errorMessage != null) {
			obj.addProperty(ERROR_MESSAGE, errorMessage);
		}

		return obj.toString();

	}
	/**
	 * method for validating the request type,whether the request type exists or not
	 * 
	 **/

	public static String validateRequestType(String requestType) throws ServiceException {

		DonationRequest donationRequest = donationService.findByRequestType(requestType);
		if (donationRequest != null) {
			throw new ServiceException(REQUEST_TYPE_EXISTS);
		}
		return requestType;
	}
	/**
	 * method to call the donation service for altering the donation request
	 * 
	 **/

	public static String updateRequest(String requestType, double requestAmount) {

		String errorMessage = null;
		String message = null;
		DonationRequest dr = null;
		DonationService ds = new DonationService();
		try {
			dr = new DonationRequest();

			dr.setRequestType(requestType);

			dr.setRequestAmount(requestAmount);
			ds.updateDonationsByAdmin(dr);
			message = SUCCESS;

		} catch (ServiceException e) {

			errorMessage = e.getMessage();
		}

		// Prepare JSON Object

		JsonObject obj = new JsonObject();
		if (message != null) {

			obj.addProperty(MESSAGE2, message);
		} else if (errorMessage != null) {
			obj.addProperty(ERROR_MESSAGE, errorMessage);
		}

		return obj.toString();

	}
	/**
	 * method to call the donation service for contribution by donor to the donation request
	 * 
	 **/

	public static String contributeRequest(double requestAmount, String requestType, int userId) {

		String errorMessage = null;
		String message = null;
		DonorActivity donorActivity = null;
		DonationService donationService = new DonationService();

		donorActivity = new DonorActivity();
		donorActivity.setAmount(requestAmount);
		donorActivity.setRequestType(requestType);
		donorActivity.setId(userId);
		try {

			donationService.contributeDonation(donorActivity);
			message = SUCCESS;
		} catch (ServiceException e) {
			errorMessage = e.getMessage();
		}
		JsonObject obj = new JsonObject();
		if (message != null) {

			obj.addProperty(MESSAGE2, message);
		} else if (errorMessage != null) {
			obj.addProperty(ERROR_MESSAGE, errorMessage);
		}

		return obj.toString();
	}

	public static void main(String[] args) {
		testAddDonation();
		listRequest();
		contributeToRequest();
		updateRequest();
	}

	private static void testAddDonation() {
		System.out.println(TEST_CASE_1_VALID_INPUT);
		String validUserJson = DonationController.addRequest("Goods", 10000);
		System.out.println(validUserJson);
		System.out.println(TEST_CASE_2_INVALID_INPUT);
		String invalidUserJson = DonationController.addRequest("Food", 10000);

		System.out.println(invalidUserJson);

	}

	private static void contributeToRequest() {
		System.out.println(TEST_CASE_1_VALID_INPUT);
		String validUserJson = DonationController.contributeRequest(1000, "Food", 1);
		System.out.println(validUserJson);
		System.out.println(TEST_CASE_2_INVALID_INPUT);
		String invalidUserJson = DonationController.contributeRequest(10000, "Education", 1);
		System.out.println(invalidUserJson);

	}

	private static void updateRequest() {
		System.out.println(TEST_CASE_1_VALID_INPUT);
		String validUserJson = DonationController.updateRequest("Food", 12000);
		System.out.println(validUserJson);
		System.out.println(TEST_CASE_2_INVALID_INPUT);
		String invalidUserJson = DonationController.updateRequest("education", 12000);

		System.out.println(invalidUserJson);

	}

}
