package com.recipesBuilder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipesBuilder.model.Recipe;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RecipeController {
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
                                "ingredients TEXT NOT NULL, " + // a JSON object representing a list of strings
                                "time INTEGER);";

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
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/recipes/new")
    public void addRecipe(Recipe recipe) {
        // Our Recipe
        String title = recipe.getTitle();
        String instructions = recipe.getInstructions();
        List<String> ingredients = recipe.getIngredients();
        int time = recipe.getTime();

        String insertQuery = "INSERT INTO recipes (title, instructions, ingredients, time) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            insertStmt.setString(1, title);
            insertStmt.setString(2, instructions);
            insertStmt.setInt(4, time);

            // Serialize ingredients into JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String ingredientsJson = objectMapper.writeValueAsString(ingredients);
            insertStmt.setString(3, ingredientsJson);

            insertStmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Fetch a recipe by id
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/recipes/{id}")
    public List<Recipe> findByRecipeId(int id) throws SQLException {
        List<Recipe> foundRecipes = new ArrayList<>();

        String selectQuery = "SELECT * " +
                            "FROM recipes WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement searchStmt = conn.prepareStatement(selectQuery)) {
            searchStmt.setInt(1, id);

            ObjectMapper objectMapper = new ObjectMapper();

            ResultSet rs = searchStmt.executeQuery();
            while (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setTitle(rs.getString("title"));
                recipe.setInstructions(rs.getString("instructions"));
                recipe.setTime(Integer.parseInt("time"));

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
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/recipes/all")
    public List<Recipe> fetchRecipes() throws SQLException {
        List<Recipe> foundRecipes = new ArrayList<>();

        String selectQuery = "SELECT * FROM recipes";

        try (Connection conn = connect(); PreparedStatement searchStmt = conn.prepareStatement(selectQuery)) {
            ObjectMapper objectMapper = new ObjectMapper();

            ResultSet rs = searchStmt.executeQuery();
            while (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setTitle(rs.getString("title"));
                recipe.setInstructions(rs.getString("instructions"));
                recipe.setTime(rs.getInt("time"));

                //Deserialize the ingredients JSON back to a list
                String ingredientsJson = rs.getString("ingredients");
                ArrayList<String> foundIngredients = objectMapper.readValue(ingredientsJson, new TypeReference<>() {
                });
                recipe.setIngredients(foundIngredients);

                foundRecipes.add(recipe);
            }
        } catch (JsonProcessingException | SQLException e) {
            e.printStackTrace();
        }


        return foundRecipes;
    }
}
