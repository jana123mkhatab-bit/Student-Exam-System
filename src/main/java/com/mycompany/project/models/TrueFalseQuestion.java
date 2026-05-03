/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.models;

/**
 *
 * @author Jana
 */

import java.util.*;


public class TrueFalseQuestion extends Question {
    private boolean correctAnswer;

    public TrueFalseQuestion(int questionId, String questionText, double mark, boolean correctAnswer) {
        super(questionId, questionText, mark);
        this.correctAnswer = correctAnswer;
    }

    // Getters
    public boolean getCorrectAnswer() { return correctAnswer; }

    // Setter (Exactly as named in the UML)
    public void setCorrectAnswerBoolean(boolean correctAnswer) { this.correctAnswer = correctAnswer; }

    @Override
    public boolean checkAnswer(String studentAnswer) {
        // Implementation: Normalize student answer ("true", "t", "1") and compare to correctAnswer.
        String normAnswer = studentAnswer.trim().toLowerCase();
        
        boolean answerBool;
        if (normAnswer.equals("true") || normAnswer.equals("t") || normAnswer.equals("1")) {
            answerBool = true;
        } else if (normAnswer.equals("false") || normAnswer.equals("f") || normAnswer.equals("0")) {
            answerBool = false;
        } else {
            return false; // Invalid input
        }
        
        return answerBool == this.correctAnswer;
    }
}