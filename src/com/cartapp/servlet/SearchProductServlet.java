package com.cartapp.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cartapp.model.SearchProduct;

/**
 * Servlet implementation class SearchProduct
 */
@WebServlet("/SearchProduct")
public class SearchProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("search");
		SearchProduct sp = new SearchProduct();
		sp.setSearch(search);
		List<SearchProduct> productList = sp.searchProductList(sp);
		if(productList == null) {
			System.out.println("List in null");
			return;
		}
		Object[] searchResult = productList.toArray();
		for(int i=0; i < searchResult.length; i++) {
			SearchProduct searchIterator = (SearchProduct) searchResult[i];
			System.out.println("I" + i);
			System.out.println(searchIterator.getSearch());
			System.out.println(searchIterator.getDescription());
			System.out.println(searchIterator.getPrice());
			System.out.println(searchIterator.getImage());
			System.out.println("********************************************");
		}
		response.setContentType("application/json");
	}

}
