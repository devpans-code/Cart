package com.cartapp.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cartapp.model.Cart;
import com.cartapp.model.CartItem;
import com.cartapp.model.Product;
import com.cartapp.model.SessionCounter;
import com.cartapp.model.User;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User userData = (User)request.getSession().getAttribute("user");
		if (request.getParameterMap().containsKey("action")) {
			String action = request.getParameter("action");
			switch (action) {
				case "remove":
					removeToCart(request, response);
					break;
			}
		}
//		response.sendRedirect("./product");
		
	}

	private void removeToCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		if(id == 0) {
			response.sendRedirect("./product");
			return;
		}
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null)
			cart = new Cart();
		cart.removeFromCart(id);
		request.getSession().setAttribute("cart", cart);
		response.sendRedirect("./product");	
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)S
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Product product = new Product();
		product.setId(Integer.parseInt(request.getParameter("id")));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		try {
			product = product.fetchProductInfo(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (product == null) {
			response.sendRedirect("./product");
			return;
		}
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null)
			cart = new Cart();
		cart.addToCart(product, quantity);
		request.getSession().setAttribute("cart", cart);
		response.sendRedirect("./product");
		return;
	}
}
