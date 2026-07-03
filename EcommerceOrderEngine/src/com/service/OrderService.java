package com.service;

import java.util.ArrayList;
import java.util.List;

import com.model.CartItem;
import com.model.Order;
import com.model.OrderStatus;
import com.model.Product;
import com.util.LoggerUtil;

public class OrderService {
	int orderCounter = 1;
	public List<Order> orders = new ArrayList<>();

	PaymentService paymentService = new PaymentService();

	public void placeOrder(int userId, List<CartItem> cart, ProductService productService) {

		LoggerUtil.log("User " + userId + " started placing order");

		// ✅ Check empty cart
		if (cart.isEmpty()) {
			LoggerUtil.log("Cart empty for user " + userId);
			System.out.println("Cart is empty!");
			return;
		}

		double total = 0;

		// ✅ Calculate total
		for (CartItem item : cart) {
			Product p = productService.getProduct(item.productId);
			total += p.price * item.quantity;
		}

		// ✅ Create order
		Order order = new Order(orderCounter++, cart, total);
		LoggerUtil.log("Order created with ID: " + order.orderId);

		// ✅ Process payment
		boolean success = paymentService.processPayment();

		if (success) {
			order.status = OrderStatus.PAID;

			LoggerUtil.log("Payment SUCCESS for order " + order.orderId);
			System.out.println("Payment successful!");

		} else {
			order.status = OrderStatus.FAILED;

			// 🔥 IMPORTANT: Failure mode log (ADD HERE)
			LoggerUtil.log("FAILURE MODE triggered for order " + order.orderId);

			LoggerUtil.log("Payment FAILED for order " + order.orderId + " → rollback");

			// 🔄 Rollback stock
			for (CartItem item : cart) {
				Product p = productService.getProduct(item.productId);
				p.stock += item.quantity;
			}

			LoggerUtil.log("Stock restored for failed order " + order.orderId);
			System.out.println("Payment failed! Stock restored.");
		}

		// ✅ Save order
		orders.add(order);
	}

	public void viewOrders() {
		for (Order o : orders) {
			System.out.println("OrderID: " + o.orderId + " Status: " + o.status + " Total: " + o.total);
		}
	}

	public void cancelOrder(int orderId) {
		for (Order o : orders) {
			if (o.orderId == orderId) {
				o.status = OrderStatus.CANCELLED;
				System.out.println("Order cancelled");
				return;
			}
		}
		System.out.println("Order not found");
	}

	public void triggerFailureMode() {
		paymentService.enableFailureMode();
		System.out.println("⚠️ Failure mode enabled: Next payment will FAIL");
	}
}
