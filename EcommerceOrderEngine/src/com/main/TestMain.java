package com.main;

import java.util.List;
import java.util.Scanner;

import com.model.CartItem;
import com.model.Product;
import com.service.CartService;
import com.service.CouponService;
import com.service.OrderService;
import com.service.ProductService;

public class TestMain {

	public static void main(String[] args) {

		CouponService couponService = new CouponService();

		try (Scanner sc = new Scanner(System.in)) {
			ProductService productService = new ProductService();
			CartService cartService = new CartService();
			OrderService orderService = new OrderService();

			int userId = 1;

			while (true) {

				// ===== FULL MENU =====
				System.out.println("\n=================================");
				System.out.println("   E-COMMERCE CLI APPLICATION");
				System.out.println("=================================");
				System.out.println("1. Add Product");
				System.out.println("2. View Products");
				System.out.println("3. Add to Cart");
				System.out.println("4. Remove from Cart");
				System.out.println("5. View Cart");
				System.out.println("6. Apply Coupon");
				System.out.println("7. Place Order");
				System.out.println("8. Cancel Order");
				System.out.println("9. View Orders");
				System.out.println("10. Low Stock Alert");
				System.out.println("11. Return Product");
				System.out.println("12. Simulate Concurrent Users");
				System.out.println("13. View Logs");
				System.out.println("14. Trigger Failure Mode");
				System.out.println("0. Exit");
				System.out.print("Enter your choice: ");

				int choice = sc.nextInt();

				switch (choice) {

				// ================= ADD PRODUCT =================
				case 1:
					System.out.println("\n--- Add Product ---");

					System.out.print("Enter Product ID: ");
					int id = sc.nextInt();

					System.out.print("Enter Product Name: ");
					String name = sc.next();

					System.out.print("Enter Stock: ");
					int stock = sc.nextInt();

					System.out.print("Enter Price: ");
					double price = sc.nextDouble();

					productService.addProduct(new Product(id, name, stock, price));
					break;

				// ================= VIEW PRODUCTS =================
				case 2:
					productService.viewProducts();
					break;

				// ================= ADD TO CART =================
				case 3:
					System.out.print("Enter Product ID: ");
					int pid = sc.nextInt();

					System.out.print("Enter Quantity: ");
					int qty = sc.nextInt();

					Product p = productService.getProduct(pid);

					if (p != null)
						cartService.addToCart(userId, p, qty);
					else
						System.out.println("❌ Product not found");
					break;

				// ================= REMOVE FROM CART =================
				case 4:
					System.out.print("Enter Product ID to remove: ");
					int removeId = sc.nextInt();
					cartService.removeFromCart(userId, removeId);
					break;

				// ================= VIEW CART =================
				case 5:
					List<CartItem> cart = cartService.getCart(userId);

					if (cart.isEmpty()) {
						System.out.println("🛒 Cart is empty!");
					} else {
						cart.forEach(c -> System.out.println("Product: " + c.productId + " Qty: " + c.quantity));
					}
					break;

				// ================= APPLY COUPON =================
				case 6:
					System.out.print("Enter Coupon Code: ");
					String coupon = sc.next();

					List<CartItem> cartForCoupon = cartService.getCart(userId);

					if (cartForCoupon.isEmpty()) {
						System.out.println("Cart is empty!");
						break;
					}

					double finalAmount = couponService.applyCoupon(coupon, cartForCoupon, productService);

					System.out.println("💰 Final Amount after discount: " + finalAmount);
					break;

				// ================= PLACE ORDER =================
				case 7:
					List<CartItem> userCart = cartService.getCart(userId);

					if (userCart.isEmpty()) {
						System.out.println("❌ Cart is empty!");
						break;
					}

					orderService.placeOrder(userId, userCart, productService);
					cartService.clearCart(userId);
					break;

				// ================= CANCEL ORDER =================
				case 8:
					System.out.print("Enter Order ID: ");
					int oid = sc.nextInt();
					orderService.cancelOrder(oid);
					break;

				// ================= VIEW ORDERS =================
				case 9:
					orderService.viewOrders();
					break;

				// ================= LOW STOCK =================
				case 10:
					productService.lowStockAlert();
					break;

				// ================= RETURN PRODUCT =================
				case 11:
					System.out.print("Enter Product ID: ");
					int rid = sc.nextInt();
					productService.returnProduct(rid);
					break;

				// ================= CONCURRENCY =================
				case 12:
					System.out.print("Enter Product ID: ");
					int cid = sc.nextInt();
					productService.simulateConcurrency(cid);
					break;

				// ================= VIEW LOGS =================
				case 13:
					com.util.LoggerUtil.viewLogs();
					break;

				// ================= FAILURE MODE =================
				case 14:
					orderService.triggerFailureMode();
					break;

				// ================= EXIT =================
				case 0:
					System.out.println("👋 Exiting...");
					System.exit(0);

				default:
					System.out.println("❌ Invalid choice!");
				}
			}
		}
	}
}