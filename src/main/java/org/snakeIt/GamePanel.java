package org.snakeIt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600; // In pixels.
    static final int SCREEN_HEIGHT = 600; // In Pixels
    static final int UNIT_SIZE = 25; // In Pixels.
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT / UNIT_SIZE);
    static final int DELAY = 75; // THe higher the number, the slower the game, and vice versa.

    final int x[] = new int[GAME_UNITS];
    final int y[] = new int [GAME_UNITS];
    int bodyParts = 6; // The body parts on the snake.
    int applesEaten = 0;
    int appleX; // The X co-ordinate of where the apple is placed. will appear randomly each time snake eats apple.
    int appleY; // Ditto.
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {


        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    public void startGame() {
    newApple();
    running = true;
    timer = new Timer(DELAY,this);
    timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    // *** GRID (Visual depiction of the grid. Can make user commnnds to turn on or off ***
    public void draw (Graphics g) {
        g.setColor(Color.darkGray);
        for (int i = 0;i<SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE,SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE);
        }
        g.setColor(Color.red);
        g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

        for (int i = 0; i < bodyParts ; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);

            } else {
                g.setColor(new Color(45,180,0)); //RGB Colours
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            }

        }

    }
    public void newApple() {
        appleX = random.nextInt((int)SCREEN_WIDTH / UNIT_SIZE )*UNIT_SIZE; // we're going to have the apple appear somewhere along the x access.
        appleY = random.nextInt((int)SCREEN_HEIGHT / UNIT_SIZE )*UNIT_SIZE; // we're going to have the apple appear somewhere along the y access.


    }
    public void move () {
             for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
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

    }
    public void checkCollisions() {

        // This checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // This checks if head touches LEFT border
        if (x[0] < 0) {
            running = false;
        }
        // This checks if head touches RIGHT border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }

        // This checks if head touches TOP border
        if (y[0] < 0) {
            running = false;
        }
        // This checks if head touches BOTTOM border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }

    }
    public void gameOver(Graphics g) {

    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (running) {
            move();
            checkAPple();
            checkCollisions();

        } repaint();

}

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
        }

    }


}


