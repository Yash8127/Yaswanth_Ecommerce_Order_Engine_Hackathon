package com.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LoggerUtil {

	private static final String FILE_NAME = "logs.txt";

	public static void log(String message) {

		String logMessage = LocalDateTime.now() + " : " + message;

		// Print to console
		System.out.println(logMessage);

		// Write to file
		try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, true))) {
			out.println(logMessage);
		} catch (IOException e) {
			System.out.println("Logging failed: " + e.getMessage());
		}
	}
	public static void viewLogs() {

	    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("logs.txt"))) {

	        String line;

	        System.out.println("\n📜 ===== SYSTEM LOGS =====");

	        while ((line = br.readLine()) != null) {
	            System.out.println(line);
	        }

	    } catch (Exception e) {
	        System.out.println("No logs found!");
	    }
	}
}