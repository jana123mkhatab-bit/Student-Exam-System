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
import java.util.List;
import java.util.Scanner;

public class StudentConsole {
    
    public static void runMenu(Student student, ExamManager examManager, ResultManager resultManager, GradingService gradingService, Scanner scanner) {
        boolean running = true;
        while (running) {
            // Added student welcome message for clarity
            System.out.println("\nWelcome, " + student.getName() + " (" + student.displayRole() + ")"); 
            System.out.println("\n=== STUDENT MENU ===");
            System.out.println("1. View Available Exams");
            System.out.println("2. Take Exam");
            System.out.println("3. View Past Results");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            
            try {
                String input = scanner.nextLine();
                if (input.isEmpty()) continue; 
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1: viewAvailableExams(examManager); break;
                    case 2: takeExam(student, examManager, resultManager, gradingService, scanner); break;
                    case 3: viewPastResults(student, resultManager); break;
                    case 4: running = false; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                 System.err.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    private static void viewAvailableExams(ExamManager examManager) {
        System.out.println("\n--- Available Exams ---");
        if (examManager.listAll().isEmpty()) {
            System.out.println("No exams currently available.");
            return;
        }
        examManager.listAll().forEach(e -> 
            System.out.println("ID: " + e.getExamId() + ", Time Limit: " + e.getTimeLimit() + " mins")
        );
    }
    
    // 💡 NEW METHOD: Checks if the student has a saved result for this exam ID.
    private static boolean hasStudentTakenExam(Student student, int examId, ResultManager resultManager) {
        // Search the manager for results matching this student ID
        List<Result> studentResults = resultManager.searchByField("studentid", String.valueOf(student.getId()));
        
        // Filter those results to see if any match the requested exam ID
        return studentResults.stream()
            .anyMatch(r -> r.getExamId() == examId);
    }

    private static void takeExam(Student student, ExamManager examManager, ResultManager resultManager, GradingService gradingService, Scanner scanner) {
        viewAvailableExams(examManager);
        
        // Check if there are any exams before asking for ID
        if (examManager.listAll().isEmpty()) return; 
        
        System.out.print("Enter Exam ID to take: ");
        
        try {
            int examId = Integer.parseInt(scanner.nextLine());
            Exam exam = examManager.searchById(examId);
            
            if (exam == null) {
                System.out.println("❌ Exam ID " + examId + " not found.");
                return;
            }

            // 🛑 CRITICAL FIX: Block retakes here
            if (hasStudentTakenExam(student, examId, resultManager)) {
                System.out.println("🛑 You have already submitted this exam (ID: " + examId + "). Retakes are not allowed.");
                return;
            }
            
            System.out.println("\n--- STARTING EXAM " + examId + " ---");
            System.out.println("You have " + exam.getTimeLimit() + " minutes.");
            
            // Core exam taking logic
            List<Answer> studentAnswers = new ArrayList<>();
            int qNum = 1; // Used for sequential question numbering
            for (Question q : exam.getQuestions()) {
                // Changed to use qNum for consistent display numbering (instead of getQuestionId)
                System.out.println("\nQ" + qNum++ + ". (" + q.getMark() + " marks)"); 
                System.out.println(q.getQuestionText());
                
                // Polymorphic display of options
                if (q instanceof MCQQuestion) {
                    MCQQuestion mcq = (MCQQuestion) q;
                    System.out.println("Choices:");
                    for (int i = 0; i < mcq.getChoices().size(); i++) {
                        System.out.println("  " + i + ": " + mcq.getChoices().get(i).trim());
                    }
                    System.out.print("Your Answer (enter choice index): ");
                } else {
                    System.out.print("Your Answer (true/false): ");
                }
                
                String answerText = scanner.nextLine().trim();
                studentAnswers.add(new Answer(q.getQuestionId(), answerText));
            }

            // Students submit exams and view results immediately.
            System.out.println("\n--- Submitting Exam and Grading... ---");
            
            // 1. Automatic Grading (Note: Assuming gradeExam signature uses student ID and Exam object)
            Result result = gradingService.gradeExam(student.getId(), exam, studentAnswers); 
            
            // 2. Save Result (The ResultManager now assigns a unique ID)
            resultManager.add(result);  // This will NOT crash with ID collision anymore
            
            // 3. View Results immediately
            System.out.println("✅ EXAM SUBMITTED AND GRADED! ✅");
            System.out.println("Student ID: " + result.getStudentId());
            System.out.println("Total Score: " + result.getScore());
            System.out.println("Final Grade: " + result.getGrade());
            
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a valid Exam ID or choice.");
        } catch (Exception e) {
            System.err.println("❌ An error occurred during the exam: " + e.getMessage());
        }
    }
    
    private static void viewPastResults(Student student, ResultManager resultManager) {
        // Students can view their results.
        System.out.println("\n--- Your Past Results ---");
        
        List<Result> studentResults = resultManager.searchByField("studentid", String.valueOf(student.getId()));
        
        if (studentResults.isEmpty()) {
            System.out.println("No past results found for you.");
            return;
        }
        
        studentResults
             .forEach(r -> 
                // Displays the unique Result ID assigned by the fixed Manager
                System.out.println("Result ID: " + r.getResultId() + 
                                   ", Exam ID: " + r.getExamId() + 
                                   ", Score: " + r.getScore() + 
                                   ", Grade: " + r.getGrade())
            );
    }
}