package com.revature.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ContributeRequest
 */
public class ContributeRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String emailId = request.getParameter("emailId");
		String requestType = request.getParameter("requestType");
		double requestAmount = Double.parseDouble(request.getParameter("requestAmount"));
		String json = DonationController.contributeRequest(requestAmount, requestType, emailId);
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();

	}
}
