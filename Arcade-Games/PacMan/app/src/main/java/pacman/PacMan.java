package pacman;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

public class PacMan extends JPanel implements ActionListener, KeyListener {
    int rowCount = 21;
    int columnCount = 19;
    int tileSize = 32;
    
    Player pacman;
    Set<Entity> walls = new HashSet<>();
    Set<Entity> foods = new HashSet<>();
    Set<Ghost> ghosts = new HashSet<>();
    Set<Entity> powerFoods = new HashSet<>();
    
    Image wallImage, blueGhostImg, orangeGhostImg, pinkGhostImg, redGhostImg, scaredGhostImg, cherryImg, powerFoodImg;
    Image pacUp, pacDown, pacLeft, pacRight;
    
    int score = 0;
    int lives = 3;
    int level = 1;
    int foodEaten;
    int cherryTimer;
    BonusItem cherry = null;
    boolean gameOver = false;
    boolean gameWon = false;
    char pendingDirection = ' ';
    Timer gameLoop;

    String[][] levels = {
        {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X       OX",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X   X   X    X",
            "XXXX XXX X XXX XXXX",
            "X        X        X",
            "X XXXXX     XXXXX X",
            "X X    bpor     X X",
            "X X XXXXXXXXXXX X X",
            "X X      P      X X",
            "X XXXXX     XXXXX X",
            "X        X        X",
            "XXXX XXX X XXX XXXX",
            "X    X   X   X    X",
            "X XX X XXXXX X XX X",
            "X                 X",
            "X XX XXX XXXXX XX X",
            "XO                X",
            "XXXXXXXXXXXXXXXXXXX"
        },
        { 
            "XXXXXXXXXXXXXXXXXXX",
            "XO                X",
            "X XXXXX XXX XXXXX X",
            "X X   X     X   X X",
            "X X X XXXXXXX X X X",
            "X X             X X", 
            "X XXXXX XXXXX XXX X",
            "X       bpor      X",
            "X XXX XXXXXXX XXX X", 
            "X        X        X",
            "X XXXXX XXXXX XXX X",
            "X X      P        X",
            "X X XXXXXXXXXXX X X",
            "X X      X        X", 
            "X XXXXX XXXXX XXXXX", 
            "X    X   X        X",
            "X XX X XXXXX X XX X",
            "X                 X",
            "X XX XXX X XXX XX X",
            "X        X       OX",
            "XXXXXXXXXXXXXXXXXXX"
        },
        {
            "XXXXXXXXXXXXXXXXXXX",
            "X                OX",
            "X XXX XXXXXXX XXX X",
            "X X             X X",
            "X X XXXXXXXXXXX X X",
            "X X      X      X X",
            "X XXX XXXXXXX XXX X",
            "X       bpor      X",
            "X XXXXX     XXXXX X",
            "X        X        X",
            "X XXXXX     XXXXX X", 
            "X        P        X",
            "X XXXXX     XXXXX X", 
            "X        X        X",
            "X XXX XXXXXXX XXX X",
            "X X             X X",
            "X X XXXXXXXXXXX X X",
            "X X      X      X X",
            "X XXX XXXXXXX XXX X",
            "XO                X",
            "XXXXXXXXXXXXXXXXXXX"
        }
    };

    public PacMan() {
        setPreferredSize(new Dimension(columnCount * tileSize, rowCount * tileSize));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        loadAssets();
        loadLevel(0);
        gameLoop = new Timer(16, this);
        gameLoop.start();
    }

    void loadAssets() {
        wallImage = new ImageIcon(getClass().getResource("/wall.png")).getImage();
        blueGhostImg = new ImageIcon(getClass().getResource("/blueGhost.png")).getImage();
        orangeGhostImg = new ImageIcon(getClass().getResource("/orangeGhost.png")).getImage();
        pinkGhostImg = new ImageIcon(getClass().getResource("/pinkGhost.png")).getImage();
        redGhostImg = new ImageIcon(getClass().getResource("/redGhost.png")).getImage();
        pacUp = new ImageIcon(getClass().getResource("/pacmanUp.png")).getImage();
        pacDown = new ImageIcon(getClass().getResource("/pacmanDown.png")).getImage();
        pacLeft = new ImageIcon(getClass().getResource("/pacmanLeft.png")).getImage();
        pacRight = new ImageIcon(getClass().getResource("/pacmanRight.png")).getImage();
        cherryImg = new ImageIcon(getClass().getResource("/cherry.png")).getImage();
        powerFoodImg = new ImageIcon(getClass().getResource("/powerFood.png")).getImage();
        scaredGhostImg = new ImageIcon(getClass().getResource("/scaredGhost.png")).getImage();
    }

    void loadLevel(int index) {
        walls.clear();
        foods.clear();
        ghosts.clear();
        foodEaten = 0;
        cherry = null;
        cherryTimer = 0;
        powerFoods.clear();
        
        int speedDivisor = 8 - (index * 2);

        String[] currentMap = levels[0];
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < currentMap[row].length(); column++) {
                char tile = currentMap[row].charAt(column);
                int x = column * tileSize;
                int y = row * tileSize;

                switch (tile) {
                    case 'X' -> walls.add(new StaticEntity(wallImage, x, y, tileSize));
                    case 'b' -> ghosts.add(new Ghost(blueGhostImg, scaredGhostImg, x, y, tileSize, speedDivisor));
                    case 'o' -> ghosts.add(new Ghost(orangeGhostImg, scaredGhostImg, x, y, tileSize, speedDivisor));
                    case 'p' -> ghosts.add(new Ghost(pinkGhostImg, scaredGhostImg, x, y, tileSize, speedDivisor));
                    case 'r' -> ghosts.add(new Ghost(redGhostImg, scaredGhostImg, x, y, tileSize, speedDivisor));
                    case 'P' -> pacman = new Player(pacRight, x, y, tileSize);
                    case ' ' -> foods.add(new FoodEntity(x + 14, y + 14));
                    case 'O' -> powerFoods.add(new PowerFoodEntity(powerFoodImg, x, y, tileSize));
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !gameWon) {
            update();
        }
        repaint();
    }

    void update() {
        if (pacman.x % tileSize == 0 && pacman.y % tileSize == 0 && pendingDirection != ' ') {
            pacman.updateDirection(pendingDirection, tileSize);
            pendingDirection = ' ';
        }

        pacman.move();
        for (Entity wall : walls) {
            if (pacman.collidesWith(wall)) {
                pacman.undoMove();
                break;
            }
        }

        for (Ghost ghost : ghosts) {
            ghost.move(walls, tileSize, pacman);
    
            if (ghost.collidesWith(pacman)) 
                if (ghost.isScared) {
                    score += 200;
                    ghost.reset();
                    ghost.isScared = false;
                } else{
                    handleDeath();
                }
        }
        
        foods.removeIf(food -> {
            if (pacman.collidesWith(food)) {
                score += 10;
                foodEaten++;
                return true;
            }
            return false;
        });    

        handleCherry();
        handlePowerFood();

        if (foods.isEmpty()) {
            if (level < 3) {
                level++;
                lives = 3;
                loadLevel(level - 1);
            } else {
                gameWon = true;
                gameLoop.stop();
            }
        }
        
        updatePacmanImage();
    }

    void handleCherry(){
        if (foodEaten > 0 && foodEaten % 50 == 0 && cherryTimer == 0 && cherry == null) {
            cherry = new BonusItem(cherryImg, 9 * tileSize, 11 * tileSize, tileSize, 100);
        }
        if (cherry != null) {
            cherryTimer ++;

            if (pacman.collidesWith(cherry)) {
                score += cherry.scoreValue;
                cherry = null;
            } else if (cherryTimer >= 600) {
                cherry = null;
                cherryTimer = 0;
            }  
        }
        if (cherry == null && foodEaten % 50 != 0) {
        cherryTimer = 0;
        }
    }

    void handlePowerFood(){
        powerFoods.removeIf(powerFood -> {
            if (pacman.collidesWith(powerFood)) {
                score += 50;
                
                for (Ghost ghost : ghosts) {
                    ghost.isScared = true;
                    ghost.scaredTimer = 600;
                }
                return true;
            }
            return false;
        });
    }


    void handleDeath() {
        lives--;
        if (lives <= 0) {
            gameOver = true;
            gameLoop.stop();
        } else {
            pacman.reset();
            pacman.stop();
            ghosts.forEach(Ghost::reset);
            cherryTimer = 0;
            cherry = null;
        }
    }

    void updatePacmanImage() {
        pacman.image = switch (pacman.direction) {
            case 'U' -> pacUp;
            case 'D' -> pacDown;
            case 'L' -> pacLeft;
            default -> pacRight;
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        walls.forEach(wall -> wall.draw(g));
        foods.forEach(food -> food.draw(g));
        ghosts.forEach(ghost -> ghost.draw(g));
        powerFoods.forEach(pf -> pf.draw(g));
        pacman.draw(g);
        if (cherry != null) {
            cherry.draw(g);
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score + " Lives: " + lives + " Level: " + level, 10, 20);
        
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 200, 300);
            g.setColor(Color.WHITE);
            g.drawString("Press SPACE to Play Again", 150, 330);
        }
        
        if (gameWon) {
            g.setColor(Color.GREEN);
            g.drawString("YOU WIN!", 210, 300);
            g.setColor(Color.WHITE);
            g.drawString("Press SPACE to Play Again", 150, 330);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameOver || gameWon) {
                score = 0;
                lives = 3;
                level = 1;
                gameOver = false;
                gameWon = false;
                loadLevel(0);
                gameLoop.start();
            }
        }

        char input = switch(e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_W  -> 'U'; 
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> 'D';
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> 'L'; 
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> 'R';
            default -> ' ';
        };
        if (input != ' ') pendingDirection = input;
    }

    @Override 
    public void keyPressed(KeyEvent e) {}
    
    @Override 
    public void keyTyped(KeyEvent e) {}

}
