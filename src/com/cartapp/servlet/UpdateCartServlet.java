package com.cartapp.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cartapp.model.Cart;
import com.cartapp.model.Product;

/**
 * Servlet implementation class UpdateCartServlet
 */
@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String[] id = request.getParameterValues("product_id");
		String[] product_quantity = request.getParameterValues("quantity");
		int product_id = 0;
		int quantity = 0;
		Product product = new Product();
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		for(int i=0; i < id.length; i++) {
			product_id = Integer.parseInt(id[i]);
			quantity = Integer.parseInt(product_quantity[i]);
			System.out.println("ID : " + product_id + " Quantity: " + quantity);
			product.setId(product_id);
			cart.updateToCart(product, quantity);
		}
		request.getSession().setAttribute("cart", cart);
		response.sendRedirect("./product");
		return;
	}

}
