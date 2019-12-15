package com.cartapp.model;

public class CartItem {
	private Product product;
	private int quantity;
	private double lineTotal;

	public CartItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
		this.lineTotal = product.getPrice() * quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}
}