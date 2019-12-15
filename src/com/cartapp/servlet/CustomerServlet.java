package com.cartapp.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cartapp.model.Product;
import com.cartapp.model.User;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User userData = (User) session.getAttribute("user");
		if(userData == null) {
			request.setAttribute("message", "Invalid access");
			request.getRequestDispatcher("login").forward(request, response);
			return;
		} 
		if(userData.getType().equals("Customer")) {  
			request.setAttribute("message", "Invalid access");
			request.getRequestDispatcher("product").forward(request, response);
			return;
		} 
		User user = new User();
		int i=0;
		
		@SuppressWarnings("rawtypes")
		Enumeration parameter = request.getParameterNames();
		int count = 0;
		while (parameter.hasMoreElements()) {
			parameter.nextElement();
			count++;
		}
		if (count >= 1) {
			String action = request.getParameter("action");
			switch (action) {
			case "change":
				try {
					changeStatus(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
		} else {
			List<User> userList = user.fetchUser();
			if(userList != null) {
				request.setAttribute("userList", userList.toArray());
				RequestDispatcher rd = request.getRequestDispatcher("admin/list-customer.jsp");
				rd.forward(request, response);
				return;
			} else {
				request.setAttribute("message", "No data found");
				RequestDispatcher rd = request.getRequestDispatcher("admin/list-customer.jsp");
				rd.forward(request, response);
				return;
			}
		}
	}

	private void changeStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));
		User u = new User();
		u.setId(id);
		User productStatus = u.fetchUserData(u);
		if(productStatus.getStatus().equals("Active")) {
			u.updateStatus(u, "Inactive", ((User) session.getAttribute("user")).getId());
		} else {
			u.updateStatus(u, "Active", ((User) session.getAttribute("user")).getId());
		}
		try {
			response.sendRedirect("customer");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
