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
        
// Must be Serializable for file persistence
public abstract class Question implements java.io.Serializable {
    private int questionId;
    private String questionText;
    private double mark;

    public Question(int questionId, String questionText, double mark) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.mark = mark;
    }

    // Getters
    public int getQuestionId() { return questionId; }
    public String getQuestionText() { return questionText; }
    public double getMark() { return mark; }

    // Setters (Exactly as named in the UML)
    public void setQuestionIdInt(int questionId) { this.questionId = questionId; }
    public void setQuestionTextString(String questionText) { this.questionText = questionText; }
    public void setMarkDouble(double mark) { this.mark = mark; }

    // Abstract method (Polymorphism)
    public abstract boolean checkAnswer(String studentAnswer);
}