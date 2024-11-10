//logic for handling/parsing HTTP requests
package com.recipesBuilder.parser;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.recipesBuilder.model.Recipe;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.net.http.HttpClient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static java.nio.file.StandardOpenOption.APPEND;

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
                ""
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
            String instructions = jsonResponse.get("instructions").asText();
            JsonNode extendedIngredients = jsonResponse.get("extendedIngredients");

            List<String> ingredients = new ArrayList<String>();
            for (JsonNode ingredient : extendedIngredients) {
                String original = ingredient.get("original").asText();
                ingredients.add(original);
            }

            recipe.setTitle(title);
            recipe.setIngredients(ingredients);
            recipe.setInstructions(instructions);
        }
        else {
            System.out.println(response.body());
        }

        return recipe;
    }

}
