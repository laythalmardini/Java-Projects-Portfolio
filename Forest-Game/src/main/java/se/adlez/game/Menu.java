package se.adlez.game;

import java.util.Scanner;
import java.util.Random;

public class Menu {
    private Forest forest;
    private Scanner scanner;

    public Menu() {
        this.forest = new Forest(); 
        this.scanner = new Scanner(System.in);
    }

    private void printMenu() {
        System.out.println(" -----------------");
        System.out.println("| 1) Create an empty forest");
        System.out.println("| 2) Print the forest");
        System.out.println("| 3) Add items (tree, rock) to the forest");
        System.out.println("| 4) List all items in the forest");
        System.out.println("| 5) Add 5 trees and 5 stones to the forest");
        System.out.println("| 6) Add player, hunter and the home");
        System.out.println("| 7) Play game");
        System.out.println("| 8) Save game to file");
        System.out.println("| 9) Load game from file");
        System.out.println("| p) Print game as JSON");
        System.out.println("| s) Save game as JSON");
        System.out.println("| m) Print menu");
        System.out.println("| qQ) Quit");
        System.out.println(" -----------------");
    }

    private String getChoice() {
        System.out.print("Enter your choice: ");
        return scanner.nextLine().trim();
    }

    public void run() {
        printMenu();
        
        while (true){

            switch (getChoice().toLowerCase()) {
                case "1" -> this.forest = new Forest(); 
                case "2" -> {
                    if (forest != null) {
                        System.out.print(forest.getGamePlan());
                    }
                }
                case "3" -> addItems(); 
                case "4" -> System.out.print(forest.listItems());
                case "5" -> addRandomItems(); 
                case "6" -> addCharacters(); 
                case "7" -> playGame(); 
                case "8" -> {
                    if (forest != null) {
                        ForestToFile.save(forest, "forest.ser");
                    }
                }
                case "9" -> {
                    Forest loaded = ForestToFile.load("forest.ser");
                    if (loaded != null) {
                        forest = loaded;
                    }
                }
                case "p" -> {
                    if (forest != null) {
                        System.out.println(ForestToJson.toJson(forest));
                    }
                }
                case "s" -> {
                    if (forest != null) {
                        ForestToJson.saveAsJson(forest, "forest.json");
                    }
                }
                case "m" -> printMenu(); 
                case "q" -> {return;} 
                default -> System.out.println("Invalid choice. Press 'm' for menu.");
            }
        }
    }

    private void addItems() {
        System.out.print("Add FirTree 🌲 (1) or Rock 🪨  (2): Enter your choice: ");
        String type = scanner.nextLine();
        System.out.print("Set position x y (separate by space): Enter your choice: ");
        try {
            String[] posStr = scanner.nextLine().split(" ");
            int x = Integer.parseInt(posStr[0]);
            int y = Integer.parseInt(posStr[1]);
            Position pos = new Position(x, y);
            
            Item item = switch (type) {
                case "1" -> new FirTree();
                case "2" -> new Rock();
                default -> null;
            };

            if (item == null) {
                return;
            }

            if (forest.addItem(item, pos)) {
                System.out.println("Added item to the forest: " + item + " " + pos.toString());
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter coordinates as: x y");
        }
    }

    private void addRandomItems() {
        Random rand = new Random();
        
        for (int i = 0; i < 5; i++) {
            for (int attempt = 0; attempt < 20; attempt++) {
                int x = rand.nextInt(Forest.WIDTH) + 1;
                int y = rand.nextInt(Forest.HEIGHT) + 1;
                Position pos = new Position(x, y);
                
                if (forest.tryAddItem(new FirTree(), pos)) {
                    System.out.println(new FirTree().toString() + " " + pos.toString());
                    break;
                }
            }
        }
        for (int i = 0; i < 5; i++) {
             for (int attempt = 0; attempt < 20; attempt++) {
                int x = rand.nextInt(Forest.WIDTH) + 1;
                int y = rand.nextInt(Forest.HEIGHT) + 1;
                Position pos = new Position(x, y);
                
                if (forest.tryAddItem(new Rock(), pos)) {
                    System.out.println(new Rock().toString() + " " + pos.toString());
                    break;
                }
            }
        }
    }

    private void addCharacters() {
        AbstractMoveableItem player = new Robot(new Position(1, 1));
        AbstractMoveableItem hunter = new Wolf(new Position(8, 5));
        AbstractMoveableItem home = new Castle(new Position(5, 8));
        
        forest.addPlayerItem(player);
        forest.addHunterItem(hunter);
        forest.addHomeItem(home);
    }

    private void playGame() {
        if (forest.getPlayer() == null || forest.getHunter() == null || forest.getHome() == null) {
            return;
        }

        String choice = "";
        
        while (!choice.equalsIgnoreCase("q") && !forest.isGameOver()) {
            System.out.print(forest.getGamePlan());
            System.out.print(forest.getStatus());
            System.out.println("Move player left=a, right=d, up=w, down=s, quit=q.");
            
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine().trim();
            
            Position move = null;
            switch (choice.toLowerCase()) {
                case "a" -> move = new Position(-1, 0); 
                case "d" -> move = new Position(1, 0);  
                case "w" -> move = new Position(0, -1); 
                case "s" -> move = new Position(0, 1);  
                case "q" -> {
                    System.out.println("Bye bye!"); 
                    return;
                }
                default -> {
                }
            }
            
            if (move == null) {
                continue;
            }
            
            forest.movePlayer(move);
        }
        
        if (forest.isGameOver()) {
            System.out.print(forest.getGamePlan());
            System.out.print(forest.getStatus());
        }
    }
}