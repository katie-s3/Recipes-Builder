package com.recipesBuilder.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipesBuilder.model.Recipe;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    private static final String DB_URL = "jdbc:sqlite:recipes.db";
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Create the recipes table
    public boolean createTable() {
        boolean created;
        String createTableQuery = "CREATE TABLE IF NOT EXISTS recipes (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "title TEXT NOT NULL, " +
                                "instructions TEXT NOT NULL, " +
                                "ingredients TEXT NOT NULL);"; // a JSON object representing a list of strings

        try (Connection conn = connect(); Statement createStmt = conn.createStatement()) {
            createStmt.execute(createTableQuery);
            created = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            created = false;
        }
        return created;
    }

    // Add a new recipe
    public void addRecipe(Recipe recipe) {
        // Our Recipe
        String title = recipe.getTitle();
        String instructions = recipe.getInstructions();
        List<String> ingredients = recipe.getIngredients();

        String insertQuery = "INSERT INTO recipes (title, instructions, ingredients) VALUES (?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            insertStmt.setString(1, title);
            insertStmt.setString(2, instructions);

            // Serialize ingredients into JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String ingredientsJson = objectMapper.writeValueAsString(ingredients);
            insertStmt.setString(3, ingredientsJson);

            insertStmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Fetch a recipe
    public List<Recipe> searchForRecipe(int id, String title, String instructions, String ingredients) throws SQLException {
        List<Recipe> foundRecipes = new ArrayList<>();

        String selectQuery = "SELECT id, title, instructions, ingredients " +
                            "FROM recipes WHERE id = ? OR " +
                            "title LIKE ? OR instructions LIKE ? OR ingredients LIKE ?";

        try (Connection conn = connect(); PreparedStatement searchStmt = conn.prepareStatement(selectQuery)) {
            searchStmt.setInt(1, id);
            searchStmt.setString(2, "%" + title + "%");
            searchStmt.setString(2, "%" + instructions + "%");
            searchStmt.setString(2, "%" + ingredients + "%");

            ObjectMapper objectMapper = new ObjectMapper();

            ResultSet rs = searchStmt.executeQuery();
            while (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setTitle(rs.getString("title"));
                recipe.setInstructions(rs.getString("instructions"));

                //Deserialize the ingredients JSON back to a list
                String ingredientsJson = rs.getString("ingredients");
                ArrayList<String> foundIngredients = objectMapper.readValue(ingredientsJson, new TypeReference<>() {
                });
                recipe.setIngredients(foundIngredients);

                foundRecipes.add(recipe);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return foundRecipes;
    }


    // Fetch all recipes
    public List<Recipe> fetchRecipes() throws SQLException {
        List<Recipe> foundRecipes = new ArrayList<>();

        String selectQuery = "SELECT id, title, instructions, ingredients FROM recipes";

        try (Connection conn = connect(); PreparedStatement searchStmt = conn.prepareStatement(selectQuery)) {
            ObjectMapper objectMapper = new ObjectMapper();

            ResultSet rs = searchStmt.executeQuery();
            while (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setTitle(rs.getString("title"));
                recipe.setInstructions(rs.getString("instructions"));

                //Deserialize the ingredients JSON back to a list
                String ingredientsJson = rs.getString("ingredients");
                ArrayList<String> foundIngredients = objectMapper.readValue(ingredientsJson, new TypeReference<>() {
                });
                recipe.setIngredients(foundIngredients);

                foundRecipes.add(recipe);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return foundRecipes;
    }
}
