package quoridor.media;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private final String filepath;
    private AudioInputStream audioInput;

    public AudioPlayer(String filepath) {
        this.filepath = filepath;
    }

    public void createAudio() throws UnsupportedAudioFileException, IOException {
        File musicPath = new File(filepath);
        if (musicPath.exists()) audioInput = AudioSystem.getAudioInputStream(musicPath);
    }

    public void playAudio() throws LineUnavailableException, IOException {
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
    }

}
