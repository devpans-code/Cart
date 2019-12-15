package com.cartapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cartapp.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("email").isEmpty() || request.getParameter("password").isEmpty()) {
			request.setAttribute("message", "Invalid input, try again later");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		User u= new User();
		u.setEmail(request.getParameter("email"));
		u.setPassword(request.getParameter("password"));
		User userData = null;
		try {
			userData = u.fetchUserInfo(u, "Active");
			if(userData == null) {
				request.setAttribute("message", "Incorrect username and password");
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getSession().invalidate();
		HttpSession session = request.getSession();
		session.setAttribute("user", userData);
		PrintWriter out = response.getWriter();
		response.sendRedirect("product");	
		return;
	}
}
