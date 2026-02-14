package se.adlez.game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ForestToJson {

    public static String toJson(Forest forest) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"WIDTH\": ").append(Forest.WIDTH).append(",\n");
        json.append("  \"HEIGHT\": ").append(Forest.HEIGHT).append(",\n");
        
        json.append("  \"items\": {");
        Map<Position, Item> items = forest.getItems();
        boolean firstItem = true;
        for (Map.Entry<Position, Item> entry : items.entrySet()) {
            if (!firstItem) {
                json.append(",");
            }
            json.append("\n    \"").append(entry.getKey().toString()).append("\": {")
                .append("\"class\": \"").append(entry.getValue().getClass().getSimpleName()).append("\", ")
                .append("\"graphic\": \"").append(entry.getValue().getGraphic()).append("\"}");
            firstItem = false;
        }
        if (!firstItem) json.append("\n  ");
        json.append("},\n");
        
        json.append("  \"player\": ").append(moveableItemToJson(forest.getPlayer())).append(",\n");
        json.append("  \"hunter\": ").append(moveableItemToJson(forest.getHunter())).append(",\n");
        json.append("  \"home\": ").append(moveableItemToJson(forest.getHome())).append(",\n");

        String escapedStatus = forest.getStatus().toString().trim().replace("\n", "\\n").replace("\"", "\\\"");
        json.append("  \"status\": \"").append(escapedStatus).append("\",\n");
        json.append("  \"gameOver\": ").append(forest.isGameOver()).append("\n");
        
        json.append("}");
        return json.toString();
    }
    
    private static String moveableItemToJson(AbstractMoveableItem item) {
        if (item == null) {
            return "null";
        }
        return String.format("{\"class\": \"%s\", \"position\": \"%s\", \"graphic\": \"%s\"}", 
            item.getClass().getSimpleName(), 
            item.getPosition().toString(),
            item.getGraphic());
    }

    public static void saveAsJson(Forest forest, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(toJson(forest));
            System.out.println("Saved JSON data to '" + fileName + "'.");
        } catch (IOException e) {
            System.out.println("Error saving JSON file: " + e.getMessage());
        }
    }
}