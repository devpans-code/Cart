package com.cartapp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cartapp.database.DatabaseService;

public class Product {
	private int id;
	private String name;
	private String description;
	private double price;
	private String image;
	private String status;
	private int quantity;
	private double row_total;
	private double total;
	private int created_by;
	private int updated_by;

	public int getCreated_by() {
		return created_by;
	}

	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}

	public int getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(int updated_by) {
		this.updated_by = updated_by;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getRow_total() {
		return row_total;
	}

	public void setRow_total(double row_total) {
		this.row_total = row_total;
	}
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	// TODO Start
	// TODO Below all function related to admin panel functionality	
	
	public List<Product> fetchProduct() throws SQLException {
		List<Product> productLists = new ArrayList<Product>();
		String sql = "SELECT id, name, description, price, image, status FROM product";
		ResultSet rs = DatabaseService.select(sql);
		if(rs != null)
		{
			Product p = null;
			while (rs.next()) {
				p = new Product();
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setDescription(rs.getString(3));
				p.setPrice(Double.parseDouble(rs.getString(4)));
				p.setImage(rs.getString(5));
				p.setStatus(rs.getString(6));
				productLists.add(p);
			}
			return productLists;
		}
		return null;
	}
	
	public int addProduct(Product product) throws SQLException {
		String sql = "INSERT INTO product (name, description, price, image, created_by, updated_by) VALUES ('"+product.getName()+"','"+product.getDescription()+"','"+product.getPrice()+"','"+product.getImage()+"', '"+product.getCreated_by()+"', '"+product.getUpdated_by()+"')";
		try {
			return DatabaseService.insert(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return 0;	
	}
	
	public Product fetchProductInfo(Product product) throws SQLException {
		String sql = "SELECT id, name, description, price, image, status FROM product WHERE id = '"+product.getId()+"'";
		ResultSet rs = DatabaseService.select(sql);
		if(rs != null)
		{
			Product p = null;
			if(rs.next()) {
				p = new Product();
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setDescription(rs.getString(3));
				p.setPrice(Double.parseDouble(rs.getString(4)));
				p.setImage(rs.getString(5));
				p.setStatus(rs.getString(6));
			}
			return p;
		}
		return null;
	}
	
	public int updateProduct(Product product) {
		String sql = null;
		if(product.getImage() == null) {
			sql = "UPDATE product SET name = '"+product.getName()+"', description = '"+product.getDescription()+"', price = '"+product.getPrice()+"', updated_by = '"+product.getUpdated_by()+"' WHERE id = '"+product.getId()+"'";
		} else {
			 sql = "UPDATE product SET name = '"+product.getName()+"', description = '"+product.getDescription()+"', price = '"+product.getPrice()+"', image = '"+getImage()+"', updated_by = '"+product.getUpdated_by()+"' WHERE id = '"+product.getId()+"'";
		}
		try {
			return DatabaseService.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return 0;
	}
	
	public int deleteProduct(Product p) {
		String sql = "DELETE FROM product WHERE id = '"+p.getId()+"'";
		try {
			return DatabaseService.delete(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateStatus(Product p, String status, int updated_by) {
		String sql = "UPDATE product SET status = '"+status+"', updated_by = '"+updated_by+"' WHERE id = '"+p.getId()+"'";
		try {
			return DatabaseService.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	// TODO End admin panel functions
	
	// TODO Start customer panel functions
	
	public List<Product> fetchActiveProduct(String status) throws SQLException {
		List<Product> productLists = new ArrayList<Product>();
		String sql = "SELECT id, name, description, price, image, status FROM product WHERE status = '"+status+"'";
		ResultSet rs = DatabaseService.select(sql);
		if(rs != null)
		{
			Product p = null;
			while (rs.next()) {
				p = new Product();
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setDescription(rs.getString(3));
				p.setPrice(Double.parseDouble(rs.getString(4)));
				p.setImage(rs.getString(5));
				p.setStatus(rs.getString(6));
				productLists.add(p);
			}
			return productLists;
		}
		return null;
	}
	
	// End customer panel function
}
