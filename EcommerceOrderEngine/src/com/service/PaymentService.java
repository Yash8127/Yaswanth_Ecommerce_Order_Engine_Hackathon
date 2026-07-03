package com.service;

import java.util.Random;

public class PaymentService {

	private boolean forceFailure = false;

	public void enableFailureMode() {
		forceFailure = true;
	}

	public boolean processPayment() {

		if (forceFailure) {
			forceFailure = false; // reset after use
			return false;
		}

		return new Random().nextBoolean();
	}
}