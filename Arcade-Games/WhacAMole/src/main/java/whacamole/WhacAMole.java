package whacamole;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.Timer;

public class WhacAMole {
    private int boardWidth = 600;
    private int boardHeight = 650;

    private JFrame frame = new JFrame("Mario: Whac A Mole");
    private JLabel textLabel = new JLabel();
    private JPanel textPanel = new JPanel();
    private JPanel boardPanel = new JPanel();
    private JButton resetButton = new JButton();

    private Tile[] board = new Tile[9];
    private ImageIcon moleIcon;
    private ImageIcon plantIcon;

    private Tile currMoleTile;
    private ArrayList<Tile> currPlantTiles = new ArrayList<>();

    private Random random = new Random();
    private Timer setMoleTimer;
    private Timer setPlantTimer;
    private int score = 0;

    public WhacAMole() {
        loadImages();
        initializeGUI();
        initializeTimers();
        startGame();
    }

    private void loadImages() {
        Image plantImg = new ImageIcon(getClass().getResource("/piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("/monty.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));
    }

    private void initializeGUI() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        
        setupResetButton();
        textPanel.add(resetButton, BorderLayout.EAST);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        setupBoard();
        frame.add(boardPanel);
        
        frame.setVisible(true);
    }

    private void setupResetButton() {
        resetButton.setFocusable(false);
        Image resetImg = new ImageIcon(getClass().getResource("/reset.png")).getImage();
        resetButton.setIcon(new ImageIcon(resetImg.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
    }

    private void setupBoard() {
        for (int i = 0; i < 9; i++) {
            Tile tile = new Tile();
            board[i] = tile;
            boardPanel.add(tile);
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    Tile clickedTile = (Tile) e.getSource();
                    handleTileClick(clickedTile);
                }
            });        
        }
    }

    private void initializeTimers() {
        setMoleTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                moveMole();
            }
        });

        setPlantTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                movePlants();
            }
        });
    }

    private void startGame() {
        setMoleTimer.start();
        setPlantTimer.start();
    }

    private void resetGame() {
        score = 0;
        textLabel.setText("Score: 0");

        setMoleTimer.stop();
        setPlantTimer.stop();

        if (currMoleTile != null) {
            currMoleTile.setIcon(null);
            currMoleTile = null;
        }

        for (Tile tile : currPlantTiles) {
            tile.setIcon(null);
        }
        currPlantTiles.clear();

        for (int i = 0; i < 9; i++) {
            board[i].setEnabled(true);
        }

        startGame();
    }

    private void handleTileClick(Tile tile) {
        if (tile == currMoleTile) {
            score += 1;
            textLabel.setText("Score: " + Integer.toString(score));
            currMoleTile.setIcon(null);
            currMoleTile = null;
        } else if (currPlantTiles.contains(tile)) {
            gameOver();
        }
    }

    private void gameOver() {
        textLabel.setText("Gameover, Score: " + Integer.toString(score));
        setMoleTimer.stop();
        setPlantTimer.stop();

        for (int i = 0; i < 9; i++) {
            board[i].setEnabled(false);
        }
    }

    private void moveMole() {
        if (currMoleTile != null) {
            currMoleTile.setIcon(null);
            currMoleTile = null;
        }

        while (currMoleTile == null) {
            int num = random.nextInt(9);
            Tile tile = board[num];

            if (!currPlantTiles.contains(tile)) {
                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        }
    }

    private void movePlants() {
        if (!currPlantTiles.isEmpty()) {
            for (Tile tile : currPlantTiles) {
                tile.setIcon(null);
            }
            currPlantTiles.clear();
        }

        while (currPlantTiles.size() < 2) {
            int num = random.nextInt(9);
            Tile tile = board[num];

            if (currMoleTile == tile || currPlantTiles.contains(tile)) {
                continue;
            }

            currPlantTiles.add(tile);
            tile.setIcon(plantIcon);
        }
    }
}
