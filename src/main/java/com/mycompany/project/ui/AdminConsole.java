package com.mycompany.project.ui;

import com.mycompany.project.services.ExamManager;
import com.mycompany.project.services.ResultManager;
import com.mycompany.project.services.UserManager;
import com.mycompany.project.users.Admin;
import com.mycompany.project.users.Instructor; 
import com.mycompany.project.users.Student; 
import com.mycompany.project.users.User;
import java.util.List; // Needed for the search method
import java.util.Scanner;

public class AdminConsole {

    // Runs the main menu loop for the Admin
    public static void runMenu(Admin admin, UserManager userManager, ExamManager examManager, ResultManager resultManager, Scanner scanner) {
        
        boolean running = true;
        while (running) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Manage Users (Add/List/Search)"); 
            System.out.println("2. Manage Exam Schedule (List/Remove)");
            System.out.println("3. View All Results");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            
            try {
                String input = scanner.nextLine();
                if (input.isEmpty()) continue; 
                
                int choice = Integer.parseInt(input);
                
                switch (choice) {
                    case 1: manageUsers(userManager, scanner); break;
                    case 2: manageExams(examManager, scanner); break;
                    case 3: viewAllResults(resultManager); break;
                    case 4: running = false; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void manageUsers(UserManager userManager, Scanner scanner) {
        System.out.println("\n--- User Management ---");
        System.out.println("1. Add New User");
        System.out.println("2. List All Users");
        System.out.println("3. Search for User"); // NEW OPTION
        System.out.print("Choose an option: ");
        
        try {
            String input = scanner.nextLine();
            if (input.isEmpty()) return;
            int choice = Integer.parseInt(input);
            
            if (choice == 1) {
                // ADD NEW USER LOGIC (Unchanged from your current code)
                System.out.println("\n--- Add New User ---");
                System.out.println("Select Role to Add:");
                System.out.println("  1. Student");
                System.out.println("  2. Instructor");
                System.out.println("  3. Admin");
                System.out.print("Enter role type (1-3): ");
                int roleChoice = Integer.parseInt(scanner.nextLine());

                System.out.print("User ID (int): ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                
                User newUser = null;

                if (roleChoice == 1) {
                    System.out.print("Student Level: ");
                    String level = scanner.nextLine();
                    newUser = new Student(id, name, email, password, level);
                } else if (roleChoice == 2) {
                    System.out.print("Instructor Department: "); 
                    String department = scanner.nextLine();       
                    newUser = new Instructor(id, name, email, password, department); 
                } else if (roleChoice == 3) {
                    newUser = new Admin(id, name, email, password); 
                } else {
                    throw new Exception("Invalid role choice.");
                }
                
                if (newUser != null) {
                    userManager.add(newUser); 
                    System.out.println("✅ " + newUser.displayRole() + " added successfully!");
                }

            } else if (choice == 2) {
                // LIST ALL USERS LOGIC (Unchanged)
                System.out.println("\n--- ALL USERS ---");
                if (userManager.listAll().isEmpty()) {
                    System.out.println("No users found.");
                } else {
                    for (User u : userManager.listAll()) {  
                        System.out.println("ID: " + u.getId() + ", Name: " + u.getName() + ", Role: " + u.displayRole());
                    }
                }
            } else if (choice == 3) {
                // NEW: SEARCH FOR USER LOGIC
                System.out.println("\n--- Search Users ---");
                System.out.println("Search by:");
                System.out.println("  1. ID");
                System.out.println("  2. Email");
                System.out.println("  3. Name (Partial Match)");
                System.out.print("Enter search type (1-3): ");
                
                String searchTypeInput = scanner.nextLine();
                if (searchTypeInput.isEmpty()) return;
                int searchType = Integer.parseInt(searchTypeInput);

                String fieldName = "";
                
                switch (searchType) {
                    case 1: 
                        System.out.print("Enter User ID: ");
                        try {
                            // Use searchById for unique IDs
                            User foundById = userManager.searchById(Integer.parseInt(scanner.nextLine()));
                            if (foundById != null) {
                                System.out.println("✅ User Found:");
                                System.out.println("ID: " + foundById.getId() + ", Name: " + foundById.getName() + ", Role: " + foundById.displayRole() + ", Email: " + foundById.getEmail());
                            } else {
                                System.out.println("❌ User with that ID not found.");
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("Invalid ID format.");
                        }
                        return; // Exit search logic after ID search
                    case 2: fieldName = "email"; break;
                    case 3: fieldName = "name"; break;
                    default: 
                        System.out.println("Invalid search type."); 
                        return;
                }
                
                // For Email/Name Search (using searchByField)
                System.out.print("Enter search value for " + fieldName + ": ");
                String searchValue = scanner.nextLine();
                
                List<User> results = userManager.searchByField(fieldName, searchValue);
                
                if (results.isEmpty()) {
                    System.out.println("❌ No users found matching '" + searchValue + "'.");
                } else {
                    System.out.println("✅ Found " + results.size() + " user(s):");
                    for (User u : results) {
                        System.out.println("ID: " + u.getId() + ", Name: " + u.getName() + ", Role: " + u.displayRole() + ", Email: " + u.getEmail());
                    }
                }
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        } catch (Exception e) {
            System.err.println("❌ Operation Failed: " + e.getMessage());
        }
    }
    
    // manageExams and viewAllResults remain unchanged
    private static void manageExams(ExamManager examManager, Scanner scanner) {
        // ... (Code for manageExams)
         System.out.println("\n--- Exam Schedule Management ---");
         System.out.println("1. List All Exams");
         System.out.println("2. Remove Exam by ID");
         System.out.print("Choose an option: ");
         
         try {
             int choice = Integer.parseInt(scanner.nextLine());
             
             if (choice == 1) {
                 examManager.listAll().forEach(e -> 
                     System.out.println("ID: " + e.getExamId() + ", Time Limit: " + e.getTimeLimit() + " mins, Questions: " + e.getQuestions().size())
                 );
             } else if (choice == 2) {
                 System.out.print("Enter Exam ID to remove: ");
                 int id = Integer.parseInt(scanner.nextLine());
                 examManager.remove(id); 
                 System.out.println("✅ Exam ID " + id + " removed successfully.");
             }
         } catch (Exception e) {
             System.err.println("❌ Operation Failed: " + e.getMessage());
         }
    }
    
    private static void viewAllResults(ResultManager resultManager) {
        // ... (Code for viewAllResults)
        System.out.println("\n--- ALL EXAM RESULTS ---");
        resultManager.listAll().forEach(r -> 
            System.out.println("Result ID: " + r.getResultId() + 
                               ", Student ID: " + r.getStudentId() + 
                               ", Exam ID: " + r.getExamId() + 
                               ", Score: " + r.getScore() + 
                               ", Grade: " + r.getGrade())
        );
    }
}