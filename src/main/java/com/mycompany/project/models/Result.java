/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.models;

/**
 *
 * @author Jana
 */



import java.io.*;
import java.util.*;

public class Result implements Serializable {
    private int resultId;
    private int examId;
    private int studentId;
    private double score;
    private String grade;

    // The constructor is fine for now, but in the Manager logic, we discussed 
    // removing resultId from here if the Manager auto-assigns it.
    public Result(int resultId, int examId, int studentId, double score, String grade) {
        this.resultId = resultId;
        this.examId = examId;
        this.studentId = studentId;
        this.score = score;
        this.grade = grade;
    }
    
    // 2. NEW 4-PARAMETER CONSTRUCTOR (Required by GradingService for creating NEW results)
    // The resultId is intentionally omitted here; it defaults to 0 and is set later by ResultManager.
    public Result(int examId, int studentId, double score, String grade) {
        this.examId = examId;
        this.studentId = studentId;
        this.score = score;
        this.grade = grade;
    }

    // Getters (These are correct)
    public int getResultId() { return resultId; }
    public int getExamId() { return examId; }
    public int getStudentId() { return studentId; }
    public double getScore() { return score; }
    public String getGrade() { return grade; }

    // Corrected Setters: Rename 'setResultIdInt' to 'setResultId'
    // This fixes the 'cannot find symbol: method setResultId(int)' error.
    
    // Original: public void setResultIdInt(int resultId) { this.resultId = resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; } // <--- CRITICAL FIX
    
    // We should also rename the other setters for consistency with standard Java practices (get/setFieldName)
    // Original: public void setExamIdInt(int examId) { this.examId = examId; }
    public void setExamId(int examId) { this.examId = examId; }
    
    // Original: public void setStudentIdInt(int studentId) { this.studentId = studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    // Original: public void setScoreDouble(double score) { this.score = score; }
    public void setScore(double score) { this.score = score; }
    
    // Original: public void setGradeString(String grade) { this.grade = grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
