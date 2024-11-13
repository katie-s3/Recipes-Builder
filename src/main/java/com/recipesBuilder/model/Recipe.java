package com.recipesBuilder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Entity
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public static int id;
    public String title;
    public List<String> ingredients;
    public String instructions;
    public int time;

    // Constructors
    //default
    public Recipe() {

    }

    // with args
    public Recipe(String title, List<String>  ingredients, String instructions, int time) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.time = time;
    }

    //Getters and setters
    public int getId() { return this.id; }

    public String getTitle() {
        return this.title;
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public int getTime() { return this.time; }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public void setIngredients(List<String> newIngredients) {
        this.ingredients = newIngredients;
    }

    public void setInstructions(String newInstructions) {
        this.instructions = newInstructions;
    }

    public void setTime(int newTime) { this.time = newTime; }
}
