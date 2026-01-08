package org.snakeIt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600; // In pixels.
    static final int SCREEN_HEIGHT = 600; // In Pixels
    static final int UNIT_SIZE = 25; // In Pixels.
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT / UNIT_SIZE);
    static int GAME_SPEED = 85; // THe lower the number, the faster the game, and vice versa.

    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6; // The body parts on the snake.
    int applesEaten = 0;
    int appleX; // The X co-ordinate of where the apple is placed. will appear randomly each time snake eats apple.
    int appleY; // Ditto.
    char direction = 'R';
    boolean snakeRunning = false;
    Timer timer;
    Random random;
    private final Audio audio;
    private int lives = 3;
    private String currentDifficulty = "Medium";
    private int totalApplesCollected = 0;
    private int initialLives = 3;

    GamePanel() {
        audio = new Audio();
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        chooseSpeed();
    }
    public void chooseSpeed() {
        String[] options = {"Hard", "Medium", "Easy"};
        int choice = JOptionPane.showOptionDialog(null, "Choose game speed:", "Speed Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        switch (choice) {
            case 0: // Hard
                GAME_SPEED = 75;
                currentDifficulty = "Hard";
                break;
            case 1: // Medium
                GAME_SPEED = 100;
                currentDifficulty = "Medium";
                break;
            case 2: // Easy
                GAME_SPEED = 150;
                currentDifficulty = "Easy";
                break;
            default:
                // Default to medium speed
                GAME_SPEED = 100;
                currentDifficulty = "Medium";
                break;
        }

        startGame();
    }
    public void startGame() {
        newApple();
        snakeRunning = true;
        bodyParts = 6; // Reset body parts

        // Only reset total apples if this is a fresh game (all lives intact)
        if (lives == initialLives) {
            totalApplesCollected = 0;
        }

        applesEaten = 0; // Reset current run score
        direction = 'R'; // Reset direction
        timer = new Timer(GAME_SPEED, this);
        timer.start();
        // Reset snake position
        x[0] = SCREEN_WIDTH / 2; // Start at the center of the screen
        y[0] = SCREEN_HEIGHT / 2;
        for (int i = 1; i < bodyParts; i++) {
            x[i] = x[0] - i * UNIT_SIZE;
            y[i] = y[0];
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // *** GRID (Visual depiction of the grid. Can make user commnnds to turn on or off ***


    public void draw(Graphics g) {
        if (snakeRunning) {
            // Draw grid
            g.setColor(Color.darkGray);
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // Draw apple with enhanced visuals (gradient effect)
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            g.setColor(new Color(139, 0, 0)); // Dark red for shadow effect
            g.fillOval(appleX + 3, appleY + 3, UNIT_SIZE - 6, UNIT_SIZE - 6);

            // Draw snake with enhanced colors
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    // Snake head - bright lime green with border
                    g.setColor(new Color(0, 255, 0)); // Bright green
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    g.setColor(new Color(0, 200, 0)); // Border
                    g.drawRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                    // Add eyes to the head
                    g.setColor(Color.black);
                    int eyeSize = 4;
                    if (direction == 'R') {
                        g.fillOval(x[i] + 15, y[i] + 5, eyeSize, eyeSize);
                        g.fillOval(x[i] + 15, y[i] + 15, eyeSize, eyeSize);
                    } else if (direction == 'L') {
                        g.fillOval(x[i] + 5, y[i] + 5, eyeSize, eyeSize);
                        g.fillOval(x[i] + 5, y[i] + 15, eyeSize, eyeSize);
                    } else if (direction == 'U') {
                        g.fillOval(x[i] + 5, y[i] + 5, eyeSize, eyeSize);
                        g.fillOval(x[i] + 15, y[i] + 5, eyeSize, eyeSize);
                    } else { // DOWN
                        g.fillOval(x[i] + 5, y[i] + 15, eyeSize, eyeSize);
                        g.fillOval(x[i] + 15, y[i] + 15, eyeSize, eyeSize);
                    }
                } else {
                    // Body with gradient effect - darker as you go back
                    int greenValue = Math.max(100, 180 - (i * 5));
                    g.setColor(new Color(45, greenValue, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    g.setColor(new Color(35, greenValue - 20, 0)); // Border
                    g.drawRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            // === SCORE DISPLAY (top center) ===
            g.setColor(Color.red);
            g.setFont(new Font("Impact", Font.BOLD, 40));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten,
                (SCREEN_WIDTH - fontMetrics.stringWidth("Score: " + applesEaten)) / 2,
                g.getFont().getSize());

            // === LIVES DISPLAY (top left) using Unicode hearts ===
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.PLAIN, 30));
            String heartsDisplay = "";
            for (int i = 0; i < lives; i++) {
                heartsDisplay += "\u2665 "; // Unicode filled heart
            }
            g.drawString(heartsDisplay, 10, 35);

            // === DIFFICULTY DISPLAY (top right) ===
            g.setColor(Color.yellow);
            g.setFont(new Font("Impact", Font.BOLD, 20));
            FontMetrics diffMetrics = getFontMetrics(g.getFont());
            String diffText = "Difficulty: " + currentDifficulty;
            g.drawString(diffText,
                SCREEN_WIDTH - diffMetrics.stringWidth(diffText) - 10,
                30);

        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE; // we're going to have the apple appear somewhere along the x access.
        appleY = random.nextInt((int) SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE; // we're going to have the apple appear somewhere along the y access.


    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        // Update the head position based on the direction
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void checkAPple() {
        if ((x[0] == appleX) && (y[0] == appleY)) { // x[0]  and y[0] are the x and y positions of the HEAD of the snake.
            applesEaten++;
            totalApplesCollected++;
            newApple();
            bodyParts++;
            audio.audioEatApple();
        }
    }

    public void checkCollisions() {

        // This checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) { // x[0]  and y[0] are the x and y positions of the HEAD of the snake.
                snakeRunning = false;
            }
        }
        // This checks if head touches LEFT border
        if (x[0] < 0) {
            snakeRunning = false; // Basically, if it touches itself, game over.
        }
        // This checks if head touches RIGHT border
        if (x[0] > SCREEN_WIDTH) {
            snakeRunning = false;
        }

        // This checks if head touches TOP border
        if (y[0] < 0) {
            snakeRunning = false;
        }
        // This checks if head touches BOTTOM border
        if (y[0] > SCREEN_HEIGHT) {
            snakeRunning = false;
        }
        if (!snakeRunning) {
            timer.stop();
            audio.audioCollision();
        }
        // Checking collisions...
        if (!snakeRunning) {
            lives--;
            if (lives > 0) {
                startGame();
            }
            // If lives == 0, gameOver will be called by draw() method
        }
    }

    public void gameOver(Graphics g) {
        // Dark overlay for better text visibility
        g.setColor(new Color(0, 0, 0, 180)); // Semi-transparent black
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // "GAME OVER" title
        g.setColor(Color.red);
        g.setFont(new Font("Impact", Font.BOLD, 75));
        FontMetrics fm1 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER",
            (SCREEN_WIDTH - fm1.stringWidth("GAME OVER")) / 2,
            150);

        // Statistics section
        g.setColor(Color.white);
        g.setFont(new Font("Impact", Font.BOLD, 30));
        FontMetrics fm2 = getFontMetrics(g.getFont());

        int statsY = 250;
        int lineSpacing = 45;

        // Final Score (last run)
        String finalScore = "Final Run Score: " + applesEaten;
        g.drawString(finalScore,
            (SCREEN_WIDTH - fm2.stringWidth(finalScore)) / 2,
            statsY);

        // Total Apples Collected
        g.setColor(Color.yellow);
        String totalScore = "Total Apples Collected: " + totalApplesCollected;
        g.drawString(totalScore,
            (SCREEN_WIDTH - fm2.stringWidth(totalScore)) / 2,
            statsY + lineSpacing);

        // Lives Used
        g.setColor(Color.red);
        int livesUsed = initialLives - lives;
        String livesText = "Lives Used: " + livesUsed + "/" + initialLives;
        g.drawString(livesText,
            (SCREEN_WIDTH - fm2.stringWidth(livesText)) / 2,
            statsY + lineSpacing * 2);

        // Difficulty
        g.setColor(Color.cyan);
        String diffText = "Difficulty: " + currentDifficulty;
        g.drawString(diffText,
            (SCREEN_WIDTH - fm2.stringWidth(diffText)) / 2,
            statsY + lineSpacing * 3);

        // Snake Length Achieved
        g.setColor(Color.green);
        String lengthText = "Max Snake Length: " + bodyParts;
        g.drawString(lengthText,
            (SCREEN_WIDTH - fm2.stringWidth(lengthText)) / 2,
            statsY + lineSpacing * 4);

        // Play Again prompt
        g.setColor(Color.lightGray);
        g.setFont(new Font("Impact", Font.PLAIN, 20));
        FontMetrics fm3 = getFontMetrics(g.getFont());
        String prompt = "Play Again?";
        g.drawString(prompt,
            (SCREEN_WIDTH - fm3.stringWidth(prompt)) / 2,
            520);

        // Show dialog
        int choice = JOptionPane.showConfirmDialog(
            null,
            "Do you want to play again?",
            "Game Over",
            JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            lives = initialLives; // Reset lives
            chooseSpeed();
        } else {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (snakeRunning) {
            move();
            checkAPple();
            checkCollisions();

        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
            break;

            case KeyEvent.VK_RIGHT:
            if (direction != 'L') {
                direction = 'R';
            }
            break;

            case KeyEvent.VK_UP:
                if(direction !='D') {

            direction = 'U';
        }   break;

            case KeyEvent.VK_DOWN:
            if(direction !='U')

    {
        direction = 'D';
    }
                break;


}
}}}




