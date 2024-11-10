package com.recipesBuilder.model;

import java.util.List;

public class Recipe {
    public String title;
    public List<String> ingredients;
    public String instructions;

    // Constructors
    //default
    public Recipe() {

    }

    // with args
    public Recipe(String title, List<String> ingredients, String instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    //Getters and setters
    public String getTitle() {
        return this.title;
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
