package com.cartapp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cartapp.database.DatabaseService;

public class SearchProduct {
	private String search;
	private String description;
	private double price = 0.0;
	private String image;

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	public List<SearchProduct> searchProductList(SearchProduct sp) {
		List<SearchProduct> productList = new ArrayList<SearchProduct>();
		String sql = "SELECT name, description, price, image FROM product WHERE name like '"+sp.getSearch()+"_%' AND status = 'Active'";
		ResultSet rs = DatabaseService.select(sql);
		if(rs != null) {
			SearchProduct product = null;
			try {
				while(rs.next()) {
					product = new SearchProduct();
					product.setSearch(rs.getString(1));
					product.setDescription(rs.getString(2));
					product.setPrice(rs.getDouble(3));
					product.setImage(rs.getString(4));
					productList.add(product);
				}
				return productList;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
