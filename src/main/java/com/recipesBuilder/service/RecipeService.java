//logic for processing recipes
package com.recipesBuilder.service;

import com.recipesBuilder.model.Recipe;
import com.recipesBuilder.parser.RecipeParser;
import com.recipesBuilder.repository.RecipeRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RecipeService {
    public Recipe fetchRecipe(String url) throws IOException, InterruptedException {
        RecipeParser parser = new RecipeParser();

        return parser.parseRecipe(url);
    }

    public void saveRecipe(Recipe recipe) throws SQLException {
        RecipeRepository repo = new RecipeRepository();

        Boolean created = repo.createTable();
        if (created) {
            repo.addRecipe(recipe);
        }
    }

    public List<Recipe> findRecipe() throws SQLException {
        RecipeRepository repo = new RecipeRepository();

        return repo.fetchRecipes();
    }

}
