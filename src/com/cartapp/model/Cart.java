package com.cartapp.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

	private List<CartItem> cartItems;
	private Double total = 0.0;
	
	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public Double getTotal() {
		return total;
	}

	/**
	 * @param product
	 * @param quantity
	 * @return
	 */
	public void addToCart(Product product, int quantity) {
		if (cartItems == null) {
			cartItems = new ArrayList<CartItem>(); 
		} 
		for (int i = 0; i < cartItems.size(); i++) {
			if(cartItems.get(i).getProduct().getId() == product.getId()) {
				quantity += cartItems.get(i).getQuantity(); 
				// TODO setQuantity() & setLineTotal()
				cartItems.get(i).setQuantity(quantity);
				cartItems.get(i).setLineTotal(cartItems.get(i).getProduct().getPrice() * quantity);
				calculateCartTotal();
				return;
			}
		}
		cartItems.add(new CartItem(product, quantity));
		calculateCartTotal();
	}
	
	public void updateToCart(Product product, int quantity) {
		if (cartItems == null) {
			cartItems = new ArrayList<CartItem>(); 
		} 
		for (int i = 0; i < cartItems.size(); i++) {
			if(cartItems.get(i).getProduct().getId() == product.getId()) {
				cartItems.get(i).setQuantity(quantity);
				cartItems.get(i).setLineTotal(cartItems.get(i).getProduct().getPrice() * quantity);
				calculateCartTotal();
				return;
			}
		}
	}

	/**
	 * @param productId Remove product from cart list
	 */
	public void removeFromCart(int productId) {
		if (cartItems == null || cartItems.size() == 0)
			return; 
		for (int i = 0; i < cartItems.size(); i++) {
			if (cartItems.get(i).getProduct().getId() == productId)
				cartItems.remove(i);
		}
		calculateCartTotal();
	}

	/**count online user using httpsessionlistener in servlet
	 * Calculate the total of cart
	 */
	private void calculateCartTotal() {
		double total = 0;
		for (int i = 0; i < cartItems.size(); i++)
			total += cartItems.get(i).getLineTotal();
		this.total = total;
	}

	private void printCartItems() {
		System.out.println("#####################Printing cart############################");
		if (cartItems == null || cartItems.size() == 0) {
			System.out.println("Cart is empty!");
			return;
		}
		for (int i = 0; i < cartItems.size(); i++) {
			CartItem ci = cartItems.get(i);
			Product p = ci.getProduct();
			System.out.println("#" + i + " - " + p.getId() + "|" + p.getName() + "|" + p.getDescription() + "|"
					+ p.getPrice() + "|" + ci.getQuantity() + "|" + ci.getLineTotal());
		}
	}
	
	public int getNumberOfItems() {
		int totalCartItem = 0;
		if (cartItems == null)
			return 0;
		for(CartItem ci : cartItems) {
			totalCartItem += ci.getQuantity();
		}
		/* return cartItems.size(); */
		return totalCartItem;
	}

	public static void main(String[] args) {
		Product p1 = new Product();
		p1.setId(1);
		p1.setName("Item 1");
		p1.setDescription("Item 1 - Description");
		p1.setPrice(100);

		Product p2 = new Product();
		p2.setId(2);
		p2.setName("Item 2");
		p2.setDescription("Item 2 - Description");
		p2.setPrice(400);

		Cart cart = new Cart();
		cart.printCartItems();//
		cart.addToCart(p1, 1);
		cart.addToCart(p2, 1);
		cart.printCartItems();
		cart.removeFromCart(1);
		cart.printCartItems();//
	}
}
