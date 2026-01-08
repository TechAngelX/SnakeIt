package org.snakeIt;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    public final String SOUND_FILE_PATH_EAT_APPLE = "src/main/resources/scoreSound.wav";
    public final String SOUND_FILE_PATH_COLLISION = "src/main/resources/collisionSound.wav";

    public void audioEatApple() {
        new Thread(() -> {
            try {
                File soundFile = new File(SOUND_FILE_PATH_EAT_APPLE);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();

                // Wait for the clip to finish playing, then close resources
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void audioCollision() {
        new Thread(() -> {
            try {
                File soundFile = new File(SOUND_FILE_PATH_COLLISION);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();

                // Wait for the clip to finish playing, then close resources
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                // Silently fail if sound file doesn't exist yet
                System.err.println("Collision sound file not found: " + SOUND_FILE_PATH_COLLISION);
            }
        }).start();
    }
}
