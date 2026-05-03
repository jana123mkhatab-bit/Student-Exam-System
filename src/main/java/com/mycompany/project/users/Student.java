/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.users;

/**
 *
 * @author Jana
 */

import java.util.*;

public class Student extends User {
    private String level;

    public Student(int id, String name, String email, String password, String level) {
        super(id, name, email, password);
        this.level = level;
    }

    // Getters
    public String getLevel() { return level; }

    // Setters (Exactly as named in the UML)
    public void setLevelString(String level) { this.level = level; }
    public void setStudentLevel(String level) { this.level = level; }

    @Override
    public String displayRole() {
        return "Student (Level: " + level + ")";
    }
}
