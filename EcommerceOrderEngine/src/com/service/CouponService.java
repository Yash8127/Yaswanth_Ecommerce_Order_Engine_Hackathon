package com.service;

import com.model.CartItem;
import com.model.Product;

import java.util.List;

public class CouponService {

	public double applyCoupon(String coupon, List<CartItem> cart, ProductService productService) {

		double total = 0;
		int totalQty = 0;

		// Calculate total
		for (CartItem item : cart) {
			Product p = productService.getProduct(item.productId);
			total += p.price * item.quantity;
			totalQty += item.quantity;
		}

		double finalAmount = total;

		// Rule 1: total > 1000 → 10% discount
		if (total > 1000) {
			finalAmount *= 0.9;
		}

		// Rule 2: quantity > 3 → extra 5%
		if (totalQty > 3) {
			finalAmount *= 0.95;
		}

		// Coupon codes
		if (coupon.equalsIgnoreCase("SAVE10")) {
			finalAmount *= 0.9;
		} else if (coupon.equalsIgnoreCase("FLAT200")) {
			finalAmount -= 200;
		}

		// Prevent negative
		return Math.max(finalAmount, 0);
	}
}
