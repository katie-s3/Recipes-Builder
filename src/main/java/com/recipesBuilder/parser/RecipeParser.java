//logic for handling/parsing HTTP requests
package com.recipesBuilder.parser;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.recipesBuilder.model.Recipe;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.net.http.HttpClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class RecipeParser {

    public Document getDocString(String url) throws IOException {
        // use Jsoup to connect to the url and extract the recipe
        Document doc = Jsoup.connect(url).get();

        return doc;
    }

    public Recipe parseRecipe(String url) throws IOException, InterruptedException {
        Recipe recipe = new Recipe(
                "Recipe Not Found",
                new ArrayList<String>(),
                " ",
                0
        );


        String spoonacularUrl = "https://api.spoonacular.com/recipes/extract";
        String token = System.getenv("API_TOKEN");
        String requestUrl = spoonacularUrl + "?url=" + url + "&apiKey=" + token;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.body());

            String title = jsonResponse.get("title").asText();
            String unformattedInstructions = jsonResponse.get("instructions").asText();
            String instructions = jsonResponse.get("instructions").asText();

            int time = jsonResponse.get("readyInMinutes").asInt();

            JsonNode extendedIngredients = jsonResponse.get("extendedIngredients");
            List<String> ingredients = new ArrayList<>();
            for (JsonNode ingredient : extendedIngredients) {
                String original = ingredient.get("original").asText();
                ingredients.add(original);
            }

            recipe.setTitle(title);
            recipe.setIngredients(ingredients);
            recipe.setInstructions(instructions);
            recipe.setTime(time);
        }
        else {
            System.out.println(response.body());
        }

        return recipe;
    }

}
