/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.services;

/**
 *
 * @author Jana
 */



import com.mycompany.project.models.Exam;
import com.mycompany.project.services.IManager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages all Exam records (exam schedule) with file persistence.
 */
public class ExamManager implements IManager<Exam> {
    
    private List<Exam> exams = new ArrayList<>();
    private static final String DATA_FILE = "exam_data.ser";

    public ExamManager() {
        loadData();
    }
    
    // --- Data Persistence (File I/O) ---
    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            exams = (List<Exam>) ois.readObject();
            System.out.println("Exam data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Exam data file not found. Starting with an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading exam data: " + e.getMessage());
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(exams);
        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Failed to save exam data.");
        }
    }
    
    // --- IManager Implementation with Error Handling ---
    
    @Override
    public void add(Exam exam) throws Exception {
        if (searchById(exam.getExamId()) != null) {
            throw new Exception("Error: Exam with ID " + exam.getExamId() + " already exists.");
        }
        exams.add(exam);
        saveData();
    }

    @Override
    public Exam edit(int id, Exam updatedExam) throws Exception {
        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).getExamId() == id) {
                if (updatedExam.getExamId() != id) {
                    throw new Exception("Error: Updated exam object ID must match target ID.");
                }
                exams.set(i, updatedExam);
                saveData();
                return updatedExam;
            }
        }
        throw new Exception("Error: Cannot edit. Exam ID " + id + " not found.");
    }

    @Override
    public boolean remove(int id) throws Exception {
        boolean removed = exams.removeIf(e -> e.getExamId() == id);
        if (removed) {
            saveData();
            return true;
        }
        throw new Exception("Error: Cannot remove. Exam ID " + id + " not found.");
    }
    
    @Override
    public Exam searchById(int id) {
        return exams.stream()
            .filter(e -> e.getExamId() == id)
            .findFirst()
            .orElse(null);
    }
    
    // Admin requirement: List (Search) by any field (time limit, ID, etc.)
    @Override
    public List<Exam> searchByField(String fieldName, String value) {
        
        return exams.stream()
            .filter(e -> {
                if (fieldName.equalsIgnoreCase("id")) return String.valueOf(e.getExamId()).equals(value);
                if (fieldName.equalsIgnoreCase("timelimit")) return String.valueOf(e.getTimeLimit()).equals(value);
                // Can add search by criteria like 'min_questions' or 'instructor'
                return false;
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Exam> listAll() {
        return new ArrayList<>(exams);
    }
}