/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.services;

/**
 *
 * @author Jana
 */


import com.mycompany.project.models.Result;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResultManager implements IManager<Result> {
    
    private List<Result> results;
    private static final String DATA_FILE = "result_data.ser";
    private int nextResultId = 1;

    public ResultManager() {
        results = new ArrayList<>();
        loadData();
    }
    
    private void findMaxId() {
        if (!results.isEmpty()) {
            nextResultId = results.stream()
                .mapToInt(Result::getResultId)
                .max()
                .orElse(0) + 1;
        } else {
            nextResultId = 1;
        }
    }

    // Removed @Override and interface dependency
    @SuppressWarnings("unchecked")
    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            results = (List<Result>) ois.readObject();
            findMaxId(); 
            System.out.println("Result data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Result data file not found. Starting with an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading result data: " + e.getMessage());
            results = new ArrayList<>();
        }
    }

    // Removed @Override and interface dependency
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(results);
        } catch (IOException e) {
            System.err.println("Error saving result data: " + e.getMessage());
        }
    }

    @Override
    public void add(Result result) throws Exception {
        // Assign the unique ID
        result.setResultId(nextResultId); 
        nextResultId++;

        results.add(result);
        saveData();
    }

    @Override
    public Result edit(int id, Result updatedEntity) throws Exception {
        // Implementation omitted for brevity
        return null;
    }

    @Override
    public boolean remove(int id) throws Exception {
        // Implementation omitted for brevity
        return results.removeIf(r -> r.getResultId() == id);
    }

    @Override
    public List<Result> listAll() {
        return new ArrayList<>(results);
    }

    @Override
    public Result searchById(int id) {
        return results.stream()
            .filter(r -> r.getResultId() == id)
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Result> searchByField(String fieldName, String value) {
        String query = value.trim().toLowerCase();

        return results.stream()
            .filter(r -> {
                if (fieldName.equalsIgnoreCase("studentid")) {
                    try {
                        return r.getStudentId() == Integer.parseInt(query);
                    } catch (NumberFormatException e) { return false; }
                }
                if (fieldName.equalsIgnoreCase("examid")) {
                    try {
                        return r.getExamId() == Integer.parseInt(query);
                    } catch (NumberFormatException e) { return false; }
                }
                return false;
            })
            .collect(Collectors.toList());
    }
}