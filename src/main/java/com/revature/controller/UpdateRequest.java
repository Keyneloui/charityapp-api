package com.revature.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateRequest
 */
public class UpdateRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String requestType = request.getParameter("requestType");
			double requestAmount = Double.parseDouble(request.getParameter("requestAmount"));
			String json = DonationController.updateRequest(requestType, requestAmount);
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
