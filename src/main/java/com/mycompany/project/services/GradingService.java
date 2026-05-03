/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.services;

/**
 *
 * @author Jana
 */



import com.mycompany.project.models.*;

import java.util.List;

// Handles the logic for "Instructor assigns automatic grading"
public class GradingService {

    public Result gradeExam(int studentId, Exam exam, List<Answer> studentAnswers) {
        double totalScore = 0.0;
        double maxScore = 0.0;
        
        // Loop through all questions in the exam
        for (Question q : exam.getQuestions()) {
            maxScore += q.getMark();
            
            // Find the student's answer corresponding to the current question ID
            Answer studentAnswer = studentAnswers.stream()
                .filter(a -> a.getQuestionId() == q.getQuestionId())
                .findFirst()
                .orElse(null);
            
            if (studentAnswer != null) {
                // Polymorphism: Calling checkAnswer() on the abstract Question object
                if (q.checkAnswer(studentAnswer.getStudentAnswer())) {
                    totalScore += q.getMark();
                }
            }
        }

        // Calculate percentage and assign grade
        double percentage = (maxScore > 0) ? (totalScore / maxScore) * 100 : 0.0;
        String grade = assignLetterGrade(percentage);

        // Create and return the Result object
        return new Result(
         //   generateResultId(), // Assuming a helper method to generate a unique ID
            exam.getExamId(),
            studentId,
            totalScore,
            grade
        );
    }
    
    // Simple grading logic
    private String assignLetterGrade(double percentage) {
        if (percentage >= 90) return "A";
        if (percentage >= 80) return "B";
        if (percentage >= 70) return "C";
        if (percentage >= 60) return "D";
        return "F";
    }
    
    private static int nextResultId = 1;
    private int generateResultId() { return nextResultId++; }
}