import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;
import java.util.ArrayList;
import javax.sound.sampled.*;

public class SnakeGame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // Contructor for JFrame
    public SnakeGame() {
        setTitle("Snake Game");
        setPreferredSize(new Dimension(600, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Call object panel then add to JFrame
        Menu menu = new Menu(); // Create an instance of the Menu class to display the main menu
        GamePanel panel = new GamePanel(); // Create an instance of the GamePanel class to display the game

        mainPanel.add(menu, "Menu");
        mainPanel.add(panel, "Game");

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        menu.getStartButton().addActionListener(e -> {
            cardLayout.show(mainPanel, "Game");
            panel.startGame();
        });
    }

    class Menu extends JPanel {
        private JButton startButton; // Start Game Button

        // Constructor for Menu Panel
        public Menu() {
            setLayout(new GridLayout(4, 1));
            JLabel title = new JLabel("Snake Game", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 36));

            startButton = new JButton("Start Game");
            startButton.setPreferredSize(new Dimension(50, 30));
            startButton.setFont(new Font("Arial", Font.PLAIN, 24));

            add(title);
            add(startButton);
        }

        // Getter method for the start button to allow the main class to add an
        // ActionListener to it
        public JButton getStartButton() {
            return startButton;

        }
    }

    // GamePanel class that extends JPanel and contains the game logic and rendering
    class GamePanel extends JPanel {
        // Snake starting point
        int x = 300; // Initial position of the snake's head
        int y = 300; // Initial position of the snake's head
        Random rand = new Random(); // Random object to generate random numbers for apple position
        int appleX, appleY; // Apple position
        Timer timer; // Timer to control the game loop
        ArrayList<Point> snakeBody = new ArrayList<>(); // ArrayList to store the points of the snake body
        // Starts at the right direction
        String direction = "RIGHT";
        int SIZE = 600; // Size of the game panel
        int TILE = 20; // Size of each tile in the game
        int score = 0; // Score variable to keep track of the player's score

        // wont stop until hits border or hitting its own body then game over
        boolean gameOver = false;

        // Constructor Logic for Game Panel
        public GamePanel() {
            setBackground(Color.BLACK); // Black Background
            setFocusable(true); // Make the panel focusable to receive keyboard input
            snakeBody.add(new Point(x, y)); // Add the starting point of the snake to the body

            apple(); // Call the apple method to generate the first apple

            // Key Listener for pressing keys
            addKeyListener(new KeyAdapter() {
                // Override the keyPressed method to handle arrow key presses and change the
                // direction of the snake
                @Override
                // Check if the right arrow key is pressed and the current direction is not left
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (!direction.equals("LEFT")) {
                            direction = "RIGHT";
                        }
                    }

                    // Check if the left arrow key is pressed and the current direction is not right
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        if (!direction.equals("RIGHT")) { // Prevent the snake from reversing direction
                            direction = "LEFT";
                        }
                    }

                    // Check if the up arrow key is pressed and the current direction is not down
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        if (!direction.equals("DOWN")) { // Prevent the snake from reversing direction
                            direction = "UP";
                        }
                    }

                    // Check if the down arrow key is pressed and the current direction is not up
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        if (!direction.equals("UP")) { // Prevent the snake from reversing direction
                            direction = "DOWN";
                        }
                    }
                }
            });
        }

        @Override
        // Override the paintComponent method to draw the snake, apple, and score on the
        // game panel
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (!gameOver) {
                for (Point point : snakeBody) {
                    g.setColor(Color.GREEN);
                    g.fillRect(point.x, point.y, 20, 20);
                }
                g.setColor(Color.RED);
                g.fillOval(appleX, appleY, 20, 20);
                g.setColor(Color.WHITE);
                g.drawString("Score: " + score, 10, 20);

            } else {
                g.setColor(Color.WHITE);
            }
        }

        // Method to generate a new apple at a random position on the game panel
        public void apple() {
            int maxX = 600 - 20;
            int maxY = 600 - 20;
            appleX = rand.nextInt(maxX / TILE + 1) * 20;
            appleY = rand.nextInt(maxY / TILE + 1) * 20;

        }

        // Method to restart the game when the game is over and ask the player if they
        // want to play again
        public void restart() {
            int choice = JOptionPane.showConfirmDialog(this, "Play Again?", "Snake Gmae",
                    JOptionPane.YES_NO_OPTION);
            switch (choice) {
                case JOptionPane.YES_OPTION:
                    // Reset game state
                    snakeBody.clear();
                    x = 300;
                    y = 300;
                    snakeBody.add(new Point(x, y));
                    direction = "RIGHT";
                    score = 0;
                    gameOver = false;
                    apple();
                    timer.start();
                    break;
                case JOptionPane.NO_OPTION:
                    System.exit(0);
                    break;
            }
        }

        // Method to start the game loop using a Timer and update the game state on each
        // tick
        public void startGame() {
            snakeBody.clear(); // Clear the snake body to reset the game state
            x = 300;
            y = 300;
            snakeBody.add(new Point(x, y)); // Add the starting point of the snake to the body
            direction = "RIGHT";
            score = 0;
            gameOver = false;
            apple(); // Generate the first apple at the start of the game
            // Stop the timer if it is already running to prevent multiple timers from
            // running simultaneously
            if (timer != null) { //
                timer.stop();
            }

            timer = new Timer(120, ex -> {
                if (!gameOver) {
                    Point currentHead = snakeBody.get(0); // Get the current head of the snake
                    Point newHead = new Point(currentHead.x, currentHead.y); // Create a new point for the new head
                                                                             // position
                    int newX = currentHead.x;
                    int newY = currentHead.y;
                    if (direction.equals("RIGHT")) {
                        newX += 20;
                    }

                    if (direction.equals("LEFT")) {
                        newX -= 20;
                    }

                    if (direction.equals("UP")) {
                        newY -= 20;
                    }

                    if (direction.equals("DOWN")) {
                        newY += 20;
                    }
                    newHead.x = newX;
                    newHead.y = newY;
                    snakeBody.add(0, newHead); // Add the new head position to the front of the snake body

                    if (newHead.x >= appleX && newHead.x < appleX + 20 && newHead.y >= appleY
                            && newHead.y < appleY + 20) {
                        playEatSound("sounds/eat.wav");
                        score++;
                        apple(); // generate new apple
                    } else {
                        // Remove tail
                        snakeBody.remove(snakeBody.size() - 1);
                    }
                    if (newHead.x < 0 || newHead.x >= SIZE || newHead.y < 0 || newHead.y >= SIZE) {
                        gameOver = true;
                        timer.stop();
                        playGameOverSound("sounds/die.wav");
                        JOptionPane.showMessageDialog(this, "Game Over");
                    }

                    for (int i = 1; i < snakeBody.size(); i++) {
                        Point p = snakeBody.get(i);
                        if (newHead.equals(p)) {
                            gameOver = true;
                            timer.stop();
                            playGameOverSound("sounds/die.wav");
                            JOptionPane.showMessageDialog(this, "Game Over");
                        }
                    }

                    // Check if the game is over and if so, call the restart method to ask the
                    // player if they want to play again
                    if (gameOver) {
                        restart();
                    }
                }
                repaint();
            });

            timer.start();
            requestFocusInWindow(); // Make sure the game panel has focus to receive keyboard input
        }

        // Method to play the eat sound when the snake eats an apple
        public void playEatSound(String sounds) {
            try {
                File audioFile = new File(sounds); // Create a File object for the audio file
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile); // Create an AudioInputStream
                                                                                           // from the audio file
                Clip clip = AudioSystem.getClip(); // Get a Clip object from the AudioSystem
                clip.open(audioStream); // Open the audio stream in the clip
                clip.start(); // Start playing the clip
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        // Method to play the game over sound when the game is over
        public void playGameOverSound(String sounds) {
            try {
                File audioFile = new File(sounds);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}