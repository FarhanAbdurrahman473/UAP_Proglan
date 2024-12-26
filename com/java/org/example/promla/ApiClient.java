package org.example.promla;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.example.UAPsmt3.Game;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/games";
    public static List<Game> getGames() throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            output.append(line);
        }

        connection.disconnect();

        return new Gson().fromJson(output.toString(), new TypeToken<List<Game>>(){}.getType());
    }

    public static void addGame(Game game) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        String input = new Gson().toJson(game);
        OutputStream os = connection.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        int responseCode = connection.getResponseCode();
        if (responseCode !=201) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = errorReader.readLine()) !=null) {
                errorResponse.append(line);
            }
            System.err.println("Error respons: " + errorResponse);
            throw new RuntimeException("Failed: HTTP error code: " + responseCode);
        }

        connection.disconnect();
    }

    public static void updateGame(Game game) throws IOException {
        URL url = new URL(BASE_URL + "/" + game.getId());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");

        String input = new Gson().toJson(game);
        OutputStream os = connection.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
        }

        connection.disconnect();
    }

    public static void deleteGame(Long id) throws IOException {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        if (connection.getResponseCode() != 204) {
            throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
        } connection.disconnect();
    }

    // Method to retrieve game by title
    public static Game getGameByTitle(String title) throws IOException {
        List<Game> games = getGames();
        return games.stream().filter(game -> game.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }
}
