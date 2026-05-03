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

public class Admin extends User {
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public String displayRole() {
        return "Administrator";
    }
}