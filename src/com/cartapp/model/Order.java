package com.cartapp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cartapp.database.DatabaseService;

public class Order {
	private int id;				// TODO order table
	private double total;		// TODO order table
	private int user_id;		// TODO order table
	private String address;		// TODO order table

	private int order_item_id;	// TODO order_item table
	private int product_id;		// TODO order_item table
	private double row_total;	// TODO order_item table
	private double price;		// TODO order_item table
	private int quantity;		// TODO order_item table
	private String name;		// TODO order_item table
	private String description;	// TODO order_item table
	private String image;		// TODO order_item table
	
	private int created_by;		// TODO for both table
	private int updated_by;		// TODO for both table
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
	
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	
	public double getRow_total() {
		return row_total;
	}
	
	public void setRow_total(double row_total) {
		this.row_total = row_total;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
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
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public int getOrder_item_id() {
		return order_item_id;
	}

	public void setOrder_item_id(int order_item_id) {
		this.order_item_id = order_item_id;
	}
	
	public Order fetchOrder(Order o, String status) {
		// TODO Auto-generated method stub
		String sql = "SELECT id, user_id, total FROM orders WHERE user_id = '"+o.getId()+"' AND status = '"+status+"'";
		ResultSet rs = DatabaseService.select(sql);
		if(rs != null) {
			Order order = null;
			try {
				while(rs.next()) {
					order = new Order();
					order.setId(rs.getInt(1));
					order.setUser_id(rs.getInt(2));
					order.setTotal(rs.getDouble(3));
				}
				return order;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public int addOrder(Order o) throws SQLException {
		String sql = "INSERT INTO orders (user_id, total, address, created_by, updated_by) VALUES ('"+o.getUser_id()+"', '"+o.getTotal()+"', '"+o.getAddress()+"','"+o.getCreated_by()+"', '"+o.getUpdated_by()+"')";
		try {
			return DatabaseService.insert(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}	
	
	public int addOrderItem(Order o) throws SQLException {
		String sql = "INSERT INTO orders_item (order_id, product_id, price, quantity, row_total, created_by, updated_by) VALUES ('"+o.getId()+"', '"+o.getProduct_id()+"', '"+o.getPrice()+"', '"+o.getQuantity()+"', '"+o.getRow_total()+"', '"+o.getCreated_by()+"', '"+o.getUpdated_by()+"')";
		try {
			return DatabaseService.insert(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Order> fetchOrderItemList(int user_id, String status) {
		String sql = "select o.id, o.total, oi.product_id, oi.price, oi.quantity, oi.row_total, p.name, p.description, p.image, oi.id from orders as o join orders_item as oi on oi.order_id = o.id join product as p on p.id = oi.product_id where o.user_id = '"+user_id+"' and o.status = '"+status+"'";
		ResultSet rs = DatabaseService.select(sql);
		if(rs != null) {
			List<Order> orderItemList = new ArrayList<Order>();
			try {
				Order order = null;
				while(rs.next()) {
					order = new Order();
					order.setId(rs.getInt(1));
					order.setTotal(rs.getDouble(2));
					order.setProduct_id(rs.getInt(3));
					order.setPrice(rs.getDouble(4));
					order.setQuantity(rs.getInt(5));
					order.setRow_total(rs.getDouble(6));
					order.setName(rs.getString(7));
					order.setDescription(rs.getString(8));
					order.setImage(rs.getString(9));
					order.setOrder_item_id(rs.getInt(10));
					orderItemList.add(order);
				}
				return orderItemList;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public int updateOrderTotal(int order_id, double total) {
		String sql = "UPDATE orders SET total = '"+total+"' WHERE id = '"+order_id+"'";
		try {
			return DatabaseService.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
