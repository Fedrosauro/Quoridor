package quoridor.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private String filepath;
    private AudioInputStream audioInput;
    public Clip clip;

    public AudioPlayer(String filepath){
        this.filepath = filepath;
    }

    public void createAudio() throws Exception{
        File musicPath = new File(filepath);
        if(musicPath.exists()) audioInput = AudioSystem.getAudioInputStream(musicPath);
        else System.out.println("File audio not found");
    }

    public void playAudio() throws LineUnavailableException, IOException {
        clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
    }
}
