package com.revature.controller;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.exception.ServiceException;
import com.revature.model.DonorActivity;
import com.revature.model.User;
import com.revature.services.UserService;

public class DonorController {
	static UserDAO userDao = new UserDAOImpl();
	static UserService userService = new UserService();
	
	/**
	 * method to call the user service for donor login
	 * 
	 **/

	public static String login(String email, String password) {

		String errorMessage = null;

		User user = null;
		try {
			user = userService.donorLogin(email, password);
			if (user == null) {
				throw new ServiceException("Invalid email/password ");
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
	
	/**
	 * method to call the user service for donor register
	 * 
	 **/

	public static String register(String name, String email, String password) {

		String errorMessage = null;
		String message = null;
		User user = null;
		try {
			user = new User();

			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);

			userService.registerDonor(user);
			message = "Success";

		} catch (ServiceException e) {

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
	/**
	 * method to call the user service for displaying the donor contributions
	 * 
	 **/

	public static String listDonor() {
		String json = null;
		List<DonorActivity> list = null;
		String errorMessage = null;
		try {

			list = userService.findAll();

		} catch (ServiceException e) {
			errorMessage = e.getMessage();

		}
		if (list != null) {
			Gson gson = new Gson();
			json = gson.toJson(list);
		}
		if (errorMessage != null) {
			JsonObject obj = new JsonObject();
			obj.addProperty("errorMessage", errorMessage);
			json = errorMessage;
		}
		System.out.println("List" + json);

		return json;

	}

	public static void main(String[] args) {

		testLogin();
		testRegister();
		listDonor();

	}

	private static void testLogin() {
		System.out.println("Test Case 1: Valid User");
		String validUserJson = DonorController.login("k@gmail.com", "1234");
		System.out.println(validUserJson);

		System.out.println("Test Case 2: Invalid User");
		String invalidUserJson = DonorController.login("invaliduser@gmail.com", "password");
		System.out.println(invalidUserJson);
	}

	private static void testRegister() {
		System.out.println("Test Case 1: Valid User");
		String validUserJson = DonorController.register("ishna", "i@gmail.com", "234");
		System.out.println(validUserJson);
		System.out.println("Test Case 2: Invalid User");
		String invalidUserJson = DonorController.register("Shil", "s@gmail.com", "234");
		System.out.println(invalidUserJson);

	}

}
