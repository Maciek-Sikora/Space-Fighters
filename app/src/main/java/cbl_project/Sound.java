package cbl_project;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[6];

    public Sound(){
        soundURL[0] = getClass().getResource("/menu.wav");
        soundURL[1] = getClass().getResource("/background.wav");
        soundURL[2] = getClass().getResource("/click.wav");
        soundURL[3] = getClass().getResource("/end.wav");
        soundURL[4] = getClass().getResource("/shoot.wav");
        soundURL[5] = getClass().getResource("/destroy.wav");
    }
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch(Exception e){

        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
