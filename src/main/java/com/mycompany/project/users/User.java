/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.users;

/**
 *
 * @author Jana
 */


import java.io.*;

import java.util.*;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String password;

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Setters (Exactly as named in the UML)
    public void setNameString(String name) { this.name = name; }
    public void setEmailString(String email) { this.email = email; }
    public void setPasswordString(String password) { this.password = password; }

    // Other methods (Exactly as named in the UML)
    public void setStudentSystem(String studentId) {
        // This method might be used for system-level ID assignment
        System.out.println("User " + name + " assigned system ID: " + studentId);
    }

    public String displayRole() {
        return "Generic User";
    }
}
