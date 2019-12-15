package com.cartapp.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cartapp.model.SendEmail;
import com.cartapp.model.SessionCounter;
import com.cartapp.model.Order;
import com.cartapp.model.User;

/**
 * Servlet implementation class OrderSevlet
 */
@WebServlet("/OrderSevlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User userData = (User)request.getSession().getAttribute("user");
		RequestDispatcher rd = request.getRequestDispatcher("place-order.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		User userData = (User) request.getSession().getAttribute("user");
		
		double total = Double.parseDouble(request.getParameter("total"));
		String address = request.getParameter("address");
		
		if(userData.getId() == 0)
			return;
		if(address == null)
			return;;
		if(total == 0)
			return;
		
		Order order = new Order();
		order.setUser_id(userData.getId());
		order.setTotal(total);
		order.setAddress(address);
		order.setCreated_by(userData.getId());
		order.setUpdated_by(userData.getId());
		int order_id = 0;
		
		try {
			order_id = order.addOrder(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(order_id == 0)
			return;
		
		String[] id = request.getParameterValues("id");
		String[] product_price = request.getParameterValues("price");
		String[] product_quantity = request.getParameterValues("quantity");
		String[] product_row_total = request.getParameterValues("row_total");
		int product_id = 0;
		double price = 0.0;
		int quantity = 0;
		double row_total = 0.0;
		
		if(id.length <= 0) { return; }
		
		int itemId = 0;
		for(int i=0; i < id.length; i++) {
			product_id = Integer.parseInt(id[i]);
			price = Double.parseDouble(product_price[i]);
			quantity = Integer.parseInt(product_quantity[i]);
			row_total = Double.parseDouble(product_row_total[i]);
			order.setId(order_id);
			order.setProduct_id(product_id);
			order.setPrice(price);
			order.setQuantity(quantity);
			order.setRow_total(row_total);
			try {
				itemId = order.addOrderItem(order);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(request.getSession().getAttribute("cart") != null && itemId != 0) {
			request.getSession().setAttribute("cart", null);
			String subject = "Place order"; 
			String message = "Hello, "+userData.getName()+"\n" 
								+ "Your order no " + order_id +" will successfully created. we will let you know the all update of you order."; 
			String sendTo = userData.getEmail();
			try {
				SendEmail.sendEmail(sendTo, subject, message);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		response.sendRedirect("./product");
		return;
	}
} 
