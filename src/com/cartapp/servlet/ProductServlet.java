package com.cartapp.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cartapp.model.Order;
import com.cartapp.model.Product;
import com.cartapp.model.SessionCounter;
import com.cartapp.model.User;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
@MultipartConfig
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("rawtypes")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User userData = (User) session.getAttribute("user");
		if(userData == null) {
			request.setAttribute("message", "Invalid access");
			request.getRequestDispatcher("login").forward(request, response);
			return;
		}
		else {
			Product product = new Product();
			Enumeration parameter = request.getParameterNames();
			int count = 0;
			while (parameter.hasMoreElements()) {
				parameter.nextElement();
				count++;
			}
			if (request.getParameterMap().containsKey("action")) {
				String action = request.getParameter("action");
				if (count >= 1) {
					switch (action) {
						case "add":
							viewAddProduct(request, response);
							break;
						case "edit":
							try {
								viewEditProdct(request, response);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							break;
						case "delete":
							deleteProduct(request, response);
							break;
						case "change":
							try {
								changeStatus(request, response);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							break;
						}
					}
			} else {
				try {
					if(userData.getType().equals("Admin")) {
						List<Product> productList = product.fetchProduct();
						request.setAttribute("productList", productList.toArray());
						RequestDispatcher rd = request.getRequestDispatcher("admin/list-product.jsp");
						rd.forward(request, response);
						return;
					} else {
						List<Product> productList = product.fetchActiveProduct("Active");
						request.setAttribute("productList", productList.toArray());
						RequestDispatcher rd = request.getRequestDispatcher("product.jsp");
						rd.forward(request, response);
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void changeStatus(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));
		Product p = new Product();
		p.setId(id);
		Product productStatus = p.fetchProductInfo(p);
		int updatedId = 0;
		if(productStatus.getStatus().equals("Active")) {
			updatedId = p.updateStatus(p, "Inactive", ((User) session.getAttribute("user")).getId());
		} else {
			updatedId = p.updateStatus(p, "Active", ((User) session.getAttribute("user")).getId());
		}
		try {
			response.sendRedirect("product");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void viewAddProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("admin/add-product.jsp");
		rd.forward(request, response);
		return;
	}

	private void viewEditProdct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String productId = request.getParameter("id");
		Product p = new Product();
		p.setId(Integer.parseInt(productId));
		Product productInfo = p.fetchProductInfo(p);
		request.setAttribute("productInfo", productInfo);
		RequestDispatcher rd = request.getRequestDispatcher("admin/edit-product.jsp");
		rd.forward(request, response);
		return;
	}
	
	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String productId = request.getParameter("id");
		Product p = new Product();
		p.setId(Integer.parseInt(productId));
		int deleteId = p.deleteProduct(p);
		if(deleteId == 1) {
			request.setAttribute("message", "Product successfully delete");
			response.sendRedirect("product");
			return;
		} else {
			request.setAttribute("message", "Product not delete	");
			response.sendRedirect("product");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("message", "Invalid request");
			request.getRequestDispatcher("/admin/add-product.jsp").forward(request, response);
		}

		List<FileItem> fileItems = null;
		try {
			fileItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		} catch (Exception ex) {
			request.setAttribute("message", "File Upload Failed due to " + ex);
			request.getRequestDispatcher("/admin/add-product.jsp").forward(request, response);
			return;
		}

		if (fileItems == null) {
			// TODO add error message - Invalid Request
			request.setAttribute("message", "Invalid request");
			request.getRequestDispatcher("/admin/add-product.jsp").forward(request, response);
			return;
		}

		Product p = new Product();
		for (FileItem item : fileItems) {
			if (item.isFormField()) {
				String fieldName = item.getFieldName();
				if (fieldName.equals("name")) {
					p.setName(item.getString());
				} else if (fieldName.equals("description")) {
					p.setDescription(item.getString());
				} else if (fieldName.equals("price") && null != item.getString()) {
					p.setPrice(Double.parseDouble(item.getString()));
				} else if (fieldName.equals("id") && null != item.getString()) {
					p.setId(Integer.parseInt(item.getString()));
				}
			} else {
				String image = new File(item.getName()).getName();
				if(image.equals("")) { } else {
					long uploadTime = new Date().getTime();
					String productImage = uploadTime + "/" + image;
					File f = new File(request.getServletContext().getRealPath("/upload") + "/" + uploadTime);
					f.mkdir();
					try {
						item.write(new File(f + File.separator + image));
					} catch (Exception e) {
						e.printStackTrace();

					}
					p.setImage(productImage);
				}
			}
		}
		int productId = 0;
		HttpSession session = request.getSession();
		if (p.getId() == 0) {
			// TODO Add new product
			p.setCreated_by(((User) session.getAttribute("user")).getId());
			p.setUpdated_by(((User) session.getAttribute("user")).getId());
			try {
				productId = p.addProduct(p);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// TODO Update existing product
			p.setUpdated_by(((User) session.getAttribute("user")).getId());
			productId = p.updateProduct(p);
		}

		if (productId == 1) {
			response.sendRedirect("product");
			return;
		} else {
			request.setAttribute("message", "Some error occured, please try again");
			request.getRequestDispatcher("/admin/add-product.jsp").forward(request, response);
			return;
		}
	}
}
