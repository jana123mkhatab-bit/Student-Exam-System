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

import java.io.Serializable;

public class Answer implements Serializable {
    private int questionId;
    private String studentAnswer;

    public Answer(int questionId, String studentAnswer) {
        this.questionId = questionId;
        this.studentAnswer = studentAnswer;
    }

    // Getters
    public int getQuestionId() { return questionId; }
    public String getStudentAnswer() { return studentAnswer; }

    // Setter (Exactly as named in the UML)
    public void setStudentAnswerString(String studentAnswer) { this.studentAnswer = studentAnswer; }
}