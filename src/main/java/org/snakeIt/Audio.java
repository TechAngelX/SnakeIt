package org.snakeIt;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    public final String SOUND_FILE_PATH_EAT_APPLE = "src/main/resources/scoreSound.wav";
    public final String SOUND_FILE_PATH_COLLISION = "src/main/resources/collisionSound.wav";

    public void audioEatApple() {
        try {
            File soundFile = new File(SOUND_FILE_PATH_EAT_APPLE);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void audioCollision() {
        try {
            File soundFile = new File(SOUND_FILE_PATH_COLLISION);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Silently fail if sound file doesn't exist yet
            System.err.println("Collision sound file not found: " + SOUND_FILE_PATH_COLLISION);
        }
    }
}
