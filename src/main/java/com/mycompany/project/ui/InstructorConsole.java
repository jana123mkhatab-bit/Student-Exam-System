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
import com.mycompany.project.models.*;
import com.mycompany.project.services.*;
import com.mycompany.project.users.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InstructorConsole {
    
    public static void runMenu(Instructor instructor, ExamManager examManager, Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== INSTRUCTOR MENU ===");
            System.out.println("1. Create New Exam");
            System.out.println("2. View My Exams");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: createNewExam(instructor, examManager, scanner); break;
                    case 2: viewMyExams(examManager, instructor); break;
                    case 3: running = false; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                 System.err.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    private static void createNewExam(Instructor instructor, ExamManager examManager, Scanner scanner) {
        try {
            System.out.println("\n--- Creating New Exam ---");
            System.out.print("Enter Exam ID (int): ");
            int examId = Integer.parseInt(scanner.nextLine());
            
            // Instructor set a time limit.
            System.out.print("Set Time Limit (in minutes): ");
            int timeLimit = Integer.parseInt(scanner.nextLine());
            
            Exam newExam = new Exam(examId, timeLimit);
            int nextQuestionId = 1;

            // Instructor designs and adds exam questions.
            boolean addingQuestions = true;
            while (addingQuestions) {
                System.out.println("\n--- Add Question ---");
                System.out.println("1. Add MCQ Question");
                System.out.println("2. Add True/False Question");
                System.out.println("3. Finish Exam Creation");
                System.out.print("Choose type: ");
                
                int typeChoice = Integer.parseInt(scanner.nextLine());
                Question q = null;
                
                if (typeChoice == 1 || typeChoice == 2) {
                    System.out.print("Question Text: ");
                    String text = scanner.nextLine();
                    System.out.print("Mark value (double): ");
                    double mark = Double.parseDouble(scanner.nextLine());
                    
                    if (typeChoice == 1) { // MCQ Question
                        System.out.print("Enter choices (comma-separated): ");
                        List<String> choices = Arrays.asList(scanner.nextLine().split(","));
                        System.out.print("Enter correct choice index (0-based): ");
                        int correctIndex = Integer.parseInt(scanner.nextLine());
                        q = new MCQQuestion(nextQuestionId, text, mark, choices, correctIndex);
                        
                    } else if (typeChoice == 2) { // True/False Question
                        System.out.print("Enter correct answer (true/false): ");
                        boolean correctAns = Boolean.parseBoolean(scanner.nextLine().toLowerCase());
                        q = new TrueFalseQuestion(nextQuestionId, text, mark, correctAns);
                    }
                    
                    if (q != null) {
                        newExam.addQuestion(q);
                        nextQuestionId++;
                        System.out.println("✅ Question added.");
                    }
                } else if (typeChoice == 3) {
                    addingQuestions = false;
                } else {
                    System.out.println("Invalid question type choice.");
                }
            }
            
            if (newExam.getQuestions().isEmpty()) {
                throw new Exception("Exam must have at least one question.");
            }
            
            examManager.add(newExam); // Save the complete exam
            System.out.println("🎉 Exam ID " + examId + " created and saved successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Error creating exam: " + e.getMessage());
        }
    }
    
    private static void viewMyExams(ExamManager examManager, Instructor instructor) {
        // Simple filter based on the Exam ID
        System.out.println("\n--- My Created Exams ---");
        // In a real system, Exam should track the instructor ID. Here, we list all.
        examManager.listAll().forEach(e -> 
             System.out.println("ID: " + e.getExamId() + ", Questions: " + e.getQuestions().size())
        );
    }
}
