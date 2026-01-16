package ca.utoronto.utm.assignment2.paint;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BackgroundMusic {

    private Clip clip;

    /**
     * Loads a WAV audio file from the given resource path.
     * @param resourcePath the path to the audio file
     */
    public BackgroundMusic(String resourcePath) {
        try {

            InputStream is = getClass().getResourceAsStream(resourcePath);
            if (is == null) {
                throw new IllegalArgumentException("Audio resource not found: " + resourcePath);
            }

            try (AudioInputStream audioIn
                    = AudioSystem.getAudioInputStream(new BufferedInputStream(is))) {

                clip = AudioSystem.getClip();
                clip.open(audioIn);
            }
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException("Unsupported audio format (must be PCM WAV): " + resourcePath, e);
        } catch (IOException e) {
            throw new RuntimeException("I/O error loading audio: " + resourcePath, e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException("Audio line unavailable for: " + resourcePath, e);
        } catch (IllegalArgumentException e) {
            System.out.println("[Warning]: Seems like audio is not available on this device, will not play audio");
        }
    }

    /**
     * Plays the clip once from the start.
     */
    public void playOnce() {
        if (clip == null) {
            return;
        }
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0); // rewind
        clip.start();
    }

    /**
     * Plays the clip continuously in a loop.
     */
    public void playLoop() {
        if (clip == null) {
            return;
        }
        if (clip.isRunning()) {
            return; // already playing
        }
        clip.stop();
        clip.setFramePosition(0); // rewind
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops the clip if it is currently playing.
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Releases audio resources and disables this clip.
     */
    public void close() {
        if (clip != null) {
            clip.stop();
            clip.flush();
            clip.close();
            clip = null;
        }
    }
}
