package com.revature.controller;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.exception.DBException;
import com.revature.model.DonationRequest;
import com.revature.model.DonorActivity;
import com.revature.model.User;
import com.revature.services.UserService;
import com.revature.util.DisplayUtil;

public class DonorController {
	static UserDAO udao = new UserDAOImpl();
	static UserService us = new UserService();

	public static String login(String email, String password) {

		String errorMessage = null;

		User user = null;
		try {
			user = us.donorLogin(email, password);
			if (user == null) {
				throw new Exception("invalid ");
			}

		} catch (Exception e) {
			// e.printStackTrace();
			errorMessage = e.getMessage();
		}

		// Prepare JSON Object
		String json = null;
		Gson gson = new Gson();
		if (user != null) {
			json = gson.toJson(user);
		} else if (user == null) {
			JsonObject obj = new JsonObject();
			obj.addProperty("errorMessage", errorMessage);
			json = obj.toString();
		}

		return json;

	}

	public static String register(String name, String email, String password) {

		String errorMessage = null;
		String message = null;
		User user = null;
		try {
			user = new User();

			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			us.registerDonor(user);
			message = "Success";

		} catch (DBException e) {
			// e.printStackTrace();
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

	public static String listDonor() {
		String json = null;
		List<DonorActivity> list = null;
		String errorMessage = null;
		try {
			// list=udao.findAll();
			list = us.findAll();

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
			json = errorMessage;
		}

		return json;

	}

	public static void main(String[] args) {

		// testLogin();
		 testRegister();
		//listDonor();

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
