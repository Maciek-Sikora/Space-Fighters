package cbl_project;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * The class handles the sound of the game.
 */
public class Sound {
    Clip clip;
    Clip background;
    URL[] soundURL = new URL[7];

    /**
     * Reads the sound files.
     */
    public Sound() {
        soundURL[0] = getClass().getResource("/menu.wav");
        soundURL[1] = getClass().getResource("/background.wav");
        soundURL[2] = getClass().getResource("/click.wav");
        soundURL[3] = getClass().getResource("/end.wav");
        soundURL[4] = getClass().getResource("/shoot.wav");
        soundURL[5] = getClass().getResource("/destroy.wav");
        soundURL[6] = getClass().getResource("/launch.wav");
    }

    /**
     * Plays the menu music when the menu is open.
     */
    void playMenuMusic() {
        try {
            background.stop();
        } catch (Exception e) {
            return;
        }
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[0]);
            background = AudioSystem.getClip();
            background.open(ais);
        } catch (Exception e) {
            return;
        }
        background.start();
        background.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Plays the game music during the match.
     */
    void playGameMusic() {
        background.stop();
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[1]);
            background = AudioSystem.getClip();
            background.open(ais);
        } catch (Exception e) {
            return;
        }
        background.start();
        background.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Plays the end music after a player wins.
     */
    void playEndMusic() {
        background.stop();
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[3]);
            background = AudioSystem.getClip();
            background.open(ais);
        } catch (Exception e) {
            return;
        }
        background.start();
        background.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Sets the music to be played depending on the specified index.
     * @param i The index of the music file.
     */
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Plays the selected sound.
     */
    public void play() {
        clip.start();
    }
    
    /**
     * Loops the selected sound.
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops the selected sound.
     */
    public void stop() {
        clip.stop();
    }
}
