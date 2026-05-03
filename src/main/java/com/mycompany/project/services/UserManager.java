/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.services;

/**
 *
 * @author Jana
 */

//package examsystem.services;
import com.mycompany.project.users.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages all User records (Admin, Student, Instructor) with file persistence.
 */
public class UserManager implements IManager<User> {
    
    private List<User> users = new ArrayList<>();
    private static final String DATA_FILE = "user_data.ser"; // .ser for serialized data

    public UserManager() {
        loadData();
    }
    
    // --- Data Persistence (File I/O) ---
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            users = (List<User>) ois.readObject();
            System.out.println("User data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found. Starting with an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading user data: " + e.getMessage());
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Failed to save user data.");
        }
    }
    
    // --- IManager Implementation with Error Handling ---
    
    @Override
    public void add(User user) throws Exception {
        if (searchById(user.getId()) != null) {
            throw new Exception("Error: User with ID " + user.getId() + " already exists.");
        }
        users.add(user);
        saveData();
    }

    @Override
    public User edit(int id, User updatedUser) throws Exception {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                // Ensure the ID of the replacement object matches the target ID
                if (updatedUser.getId() != id) {
                    throw new Exception("Error: Updated user object ID must match target ID.");
                }
                users.set(i, updatedUser);
                saveData();
                return updatedUser;
            }
        }
        throw new Exception("Error: Cannot edit. User ID " + id + " not found.");
    }

    @Override
    public boolean remove(int id) throws Exception {
        boolean removed = users.removeIf(u -> u.getId() == id);
        if (removed) {
            saveData();
            return true;
        }
        throw new Exception("Error: Cannot remove. User ID " + id + " not found.");
    }
    
    @Override
    public User searchById(int id) {
        return users.stream()
            .filter(u -> u.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    // Admin requirement: List (Search) by any field (name, email, role, etc.)
    @Override
    public List<User> searchByField(String fieldName, String value) {
        String query = value.trim().toLowerCase();
        
        return users.stream()
            .filter(u -> {
                if (fieldName.equalsIgnoreCase("name")) return u.getName().toLowerCase().contains(query);
                if (fieldName.equalsIgnoreCase("email")) return u.getEmail().toLowerCase().contains(query);
                if (fieldName.equalsIgnoreCase("role")) return u.displayRole().toLowerCase().contains(query);
                // Add more fields here as needed
                return false;
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public List<User> listAll() {
        // Return a defensive copy to prevent outside modification of the internal list
        return new ArrayList<>(users); 
    }
}