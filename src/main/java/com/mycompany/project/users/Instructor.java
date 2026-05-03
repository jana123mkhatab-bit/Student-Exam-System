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
import java.io.*;

public class Instructor extends User {
    private String department;

    public Instructor(int id, String name, String email, String password, String department) {
        super(id, name, email, password);
        this.department = department;
    }

    // Getters
    public String getDepartment() { return department; }

    // Setters (Exactly as named in the UML)
    public void setDepartmentString(String department) { this.department = department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String displayRole() {
        return "Instructor (Dept: " + department + ")";
    }
}