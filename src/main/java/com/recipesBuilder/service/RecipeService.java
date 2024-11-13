package com.recipesBuilder.service;

import com.recipesBuilder.controller.RecipeController;
import com.recipesBuilder.model.Recipe;
import com.recipesBuilder.parser.RecipeParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RecipeService {
    public Recipe fetchRecipe(String url) throws IOException, InterruptedException {
        RecipeParser parser = new RecipeParser();

        return parser.parseRecipe(url);
    }

    public void saveRecipe(Recipe recipe) throws SQLException {
        RecipeController repo = new RecipeController();

        Boolean created = repo.createTable();
        if (created) {
            repo.addRecipe(recipe);
        }
    }

    public List<Recipe> findRecipe() throws SQLException {
        RecipeController repo = new RecipeController();

        return repo.fetchRecipes();
    }

}
