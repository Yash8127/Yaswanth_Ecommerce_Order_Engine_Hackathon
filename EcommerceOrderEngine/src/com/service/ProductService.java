package com.service;

import java.util.HashMap;
import java.util.Map;

import com.model.Product;
import com.util.LoggerUtil;

public class ProductService {
	public Map<Integer, Product> products = new HashMap<>();

	public void addProduct(Product p) {

		if (products.containsKey(p.id)) {
			LoggerUtil.log("Duplicate product ID attempt: " + p.id);
			System.out.println("Product ID already exists!");
			return;
		}

		products.put(p.id, p);

		LoggerUtil.log("Product added: " + p.name + " (ID: " + p.id + ")");
		System.out.println("Product added!");
	}

	public void viewProducts() {
		for (Product p : products.values()) {
			System.out.println(p.id + " " + p.name + " Stock:" + p.stock + " Price:" + p.price);
		}
	}

	public Product getProduct(int id) {
		return products.get(id);
	}

	public void lowStockAlert() {
		for (Product p : products.values()) {
			if (p.stock < 5) {
				System.out.println("Low stock: " + p.name);
			}
		}
	}

	public void returnProduct(int productId) {
		Product p = products.get(productId);
		if (p != null) {
			p.stock += 1;
			System.out.println("Product returned");
		}
	}

	public void simulateConcurrency(int productId) {

		Product p = products.get(productId);

		if (p == null) {
			System.out.println("Product not found");
			return;
		}

		Runnable task = () -> {
			synchronized (p) {
				if (p.stock > 0) {
					p.stock--;
					System.out.println(Thread.currentThread().getName() + " bought item");
				} else {
					System.out.println("Out of stock");
				}
			}
		};

		Thread t1 = new Thread(task);
		Thread t2 = new Thread(task);

		t1.start();
		t2.start();
	}
}
