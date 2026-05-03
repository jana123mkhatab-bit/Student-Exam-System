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

//import java.util.ArrayList;
//import java.util.List;
import java.io.*;

public class Exam implements Serializable {
    private int examId;
    private int timeLimit;
    private List<Question> questions;

    public Exam(int examId, int timeLimit) {
        this.examId = examId;
        this.timeLimit = timeLimit;
        this.questions = new ArrayList<>();
    }

    // Getters
    public int getExamId() { return examId; }
    public int getTimeLimit() { return timeLimit; }
    public List<Question> getQuestions() { return questions; }

    // Setters (Exactly as named in the UML)
    public void setExamIdInt(int examId) { this.examId = examId; }
    public void setTimeLimitInt(int timeLimit) { this.timeLimit = timeLimit; }
    public void setQuestionsList(List<Question> questions) { this.questions = questions; }

    // Other methods
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void removeQuestion(Question question) {
        this.questions.remove(question);
    }
}