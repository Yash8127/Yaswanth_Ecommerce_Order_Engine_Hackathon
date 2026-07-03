package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.CartItem;
import com.model.Product;
import com.util.LoggerUtil;

public class CartService {
	public Map<Integer, List<CartItem>> carts = new HashMap<>();

	public void addToCart(int userId, Product p, int qty) {

		if (p.stock < qty) {
			LoggerUtil.log("Stock insufficient for product ID: " + p.id);
			System.out.println("Not enough stock!");
			return;
		}

		p.stock -= qty;

		carts.putIfAbsent(userId, new ArrayList<>());
		carts.get(userId).add(new CartItem(p.id, qty));

		LoggerUtil.log("User " + userId + " added product " + p.id + " qty " + qty);
		System.out.println("Added to cart!");
	}

	public List<CartItem> getCart(int userId) {
		return carts.getOrDefault(userId, new ArrayList<>());
	}

	public void clearCart(int userId) {
		carts.remove(userId);
	}

	public void removeFromCart(int userId, int productId) {
		List<CartItem> cart = carts.get(userId);
		if (cart != null) {
			cart.removeIf(item -> item.productId == productId);
			System.out.println("Removed from cart");
		}
	}
}
