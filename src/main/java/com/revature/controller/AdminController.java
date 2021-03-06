package com.revature.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.exception.ServiceException;
import com.revature.model.Admin;
import com.revature.services.AdminService;

public class AdminController {
	
	/**
	 * method to call the admin service for admin login
	 * 
	 **/

	public static String login(String email, String password) {
		AdminService adminService = new AdminService();

		String errorMessage = null;

		Admin user = null;
		try {
			user = adminService.adminLogin(email, password);

			if (user == null) {
				throw new ServiceException("Invalid Email/Password");
			}

		} catch (ServiceException e) {
			errorMessage = e.getMessage();
		}

		// Prepare JSON Object
		String json = null;
		Gson gson = new Gson();
		if (user != null) {
			json = gson.toJson(user);
		} else {
			JsonObject obj = new JsonObject();
			obj.addProperty("errorMessage", errorMessage);
			json = obj.toString();
		}

		return json;

	}

	public static void main(String[] args) {

		testLogin();

	}

	private static void testLogin() {
		System.out.println("Test Case 1: Valid User");
		String validUserJson = AdminController.login("k@gmail.com", "123");
		System.out.println(validUserJson);

		System.out.println("Test Case 2: Invalid User");
		String invalidUserJson = AdminController.login("invaliduser@gmail.com", "password");
		System.out.println(invalidUserJson);
	}
}
