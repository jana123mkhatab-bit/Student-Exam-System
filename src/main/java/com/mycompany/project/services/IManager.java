/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.project.services;

/**
 *
 * @author Jana
 */



import java.util.List;

/**
 * Defines the standard CRUD and search operations for all entity managers 
 * in the system (Users, Exams, Results).
 * @param <T> The type of the entity (e.g., User, Exam, Result)
 */
public interface IManager<T> {
    
    /** Adds a new entity to the manager's collection. */
    void add(T entity) throws Exception;
    
    /** Finds and updates an existing entity. Returns the updated entity. */
    T edit(int id, T updatedEntity) throws Exception;
    
    /** Removes an entity by its ID. */
    boolean remove(int id) throws Exception;
    
    /** Returns a list of all entities. */
    List<T> listAll();
    
    /** Searches for a single entity by its unique ID. */
    T searchById(int id);
    
    /** Searches for entities by a specific field and value. */
    List<T> searchByField(String fieldName, String value);
}
