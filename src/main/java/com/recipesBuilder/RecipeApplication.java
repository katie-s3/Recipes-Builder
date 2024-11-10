//main application entry point
package com.recipesBuilder;

import com.recipesBuilder.model.Recipe;
import com.recipesBuilder.service.RecipeService;

import java.util.List;
import java.util.Scanner;

public class RecipeApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (scanner) {
            RecipeService recipeService = new RecipeService();
            System.out.println("Please enter the recipe's URL: ");
            String url = scanner.next();
            Recipe parsedRecipe = recipeService.fetchRecipe(url);

            // If it was successfully found, save our new recipe
            if (!parsedRecipe.getTitle().equals("Recipe Not Found")) {
                recipeService.saveRecipe(parsedRecipe);
            }
            List<Recipe> allRecipes = recipeService.findRecipe();
            for (Recipe recipe : allRecipes) {
                System.out.println("----------------");
                displayRecipe(recipe);
            }
        } catch (Exception e) {
            System.out.println("Error parsing recipe: " + e.getMessage());
        }
    }

    private static void displayRecipe(Recipe recipe) {
        System.out.println("\nRecipe Title: " + recipe.getTitle());
        System.out.println("Ingredients:");
        for (Object ingredient : recipe.getIngredients()) {
            System.out.println(" - " + ingredient);
        }
        System.out.println("Instructions: " + recipe.getInstructions());
    }
}
