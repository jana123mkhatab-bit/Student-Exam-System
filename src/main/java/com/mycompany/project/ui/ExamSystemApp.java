/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.ui;

/**
 *
 * @author Jana
 */



import com.mycompany.project.services.ExamManager;
import com.mycompany.project.services.*;
import com.mycompany.project.users.*;

import java.util.Scanner;

public class ExamSystemApp {
    
    // Static instances of the managers to be used across the application
    private static UserManager userManager = new UserManager();
    private static ExamManager examManager = new ExamManager();
    private static ResultManager resultManager = new ResultManager();
    private static GradingService gradingService = new GradingService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("  📚 Online Examination System Initializing...");
        System.out.println("=============================================");
        
        // 1. Initial Setup (Create a default Admin if none exists)
        setupInitialData();
        
        // 2. Start the main loop
        runLoginScreen();
    }
    
    private static void setupInitialData() {
        // Ensures the system is usable immediately by an admin
        if (userManager.listAll().isEmpty()) {
            try {
                // Hardcoded default Admin for first run
                Admin defaultAdmin = new Admin(1, "System Admin", "admin@sys.com", "admin123");
                userManager.add(defaultAdmin);
                System.out.println("Default Admin created. Total users: " + userManager.listAll().size());
            } catch (Exception e) {
                System.err.println("Could not create default admin: " + e.getMessage());
            }
        }
    }
    
    private static void runLoginScreen() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        // Search by email and authenticate
        User loggedInUser = userManager.searchByField("email", email).stream()
            .filter(u -> u.getPassword().equals(password))
            .findFirst()
            .orElse(null);
        
        if (loggedInUser != null) {
            System.out.println("\nWelcome, " + loggedInUser.getName() + " (" + loggedInUser.displayRole() + ")");
            // 3. Delegate to the correct role-based menu
            navigateToRoleMenu(loggedInUser);
        } else {
            System.out.println("\n❌ Login failed. Invalid credentials.");
            runLoginScreen(); // Try again
        }
    }

    private static void navigateToRoleMenu(User user) {
        if (user instanceof Admin) {
            AdminConsole.runMenu((Admin) user, userManager, examManager, resultManager, scanner);
        } else if (user instanceof Instructor) {
            InstructorConsole.runMenu((Instructor) user, examManager, scanner);
        } else if (user instanceof Student) {
            StudentConsole.runMenu((Student) user, examManager, resultManager, gradingService, scanner);
        } else {
            System.out.println("Unknown user role. Exiting.");
        }
    }
}