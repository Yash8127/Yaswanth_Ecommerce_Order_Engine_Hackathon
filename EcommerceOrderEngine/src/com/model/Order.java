package com.model;

import java.util.List;

public class Order {
	public int orderId;
	public List<CartItem> items;
	public double total;
	public OrderStatus status;

	public Order(int orderId, List<CartItem> items, double total) {
		this.orderId = orderId;
		this.items = items;
		this.total = total;
		this.status = OrderStatus.CREATED;
	}
}
