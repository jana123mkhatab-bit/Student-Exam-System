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


import java.util.List;

public class MCQQuestion extends Question {
    private List<String> choices;
    private int correctAnswerIndex; // Assuming int is used to index the correct choice

    public MCQQuestion(int questionId, String questionText, double mark, List<String> choices, int correctAnswerIndex) {
        super(questionId, questionText, mark);
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    // Getters
    public List<String> getChoices() { return choices; }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }

    // Setters (Exactly as named in the UML)
    public void setChoicesList(List<String> choices) { this.choices = choices; }
    public void setCorrectAnswerIndexInt(int correctAnswerIndex) { this.correctAnswerIndex = correctAnswerIndex; }

    //@Override
    public boolean checkAnswer(String studentAnswer) {
        // Implementation: Check if the student's answer (assumed to be a choice index string) matches the correct index.
        try {
            return Integer.parseInt(studentAnswer.trim()) == this.correctAnswerIndex;
        } catch (NumberFormatException e) {
            // Handle cases where the student might input the choice text instead of the index
            return false; 
        }
    }
}