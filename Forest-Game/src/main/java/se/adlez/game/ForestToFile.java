package se.adlez.game;

import java.io.*;

public class ForestToFile {

    public static void save(Forest forest, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(forest);
            System.out.println("Saved serialized forest to file '" + fileName + "'.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static Forest load(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            System.out.println("Loaded serialized forest from file '" + fileName + "'.");
            return (Forest) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
            return null;
        }
    }
}