package com.revature.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String name = request.getParameter("name");
			String emailId = request.getParameter("email_id");
			String password = request.getParameter("password");
			String json = DonorController.register(name, emailId, password);
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
