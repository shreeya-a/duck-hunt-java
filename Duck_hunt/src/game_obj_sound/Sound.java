package game_obj_sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound extends IOException {
    private final URL shoot;
    private final URL hit;
    private final URL destroy;
    private final URL destroyPlayer;
    public Sound(){
        this.shoot = this.getClass().getClassLoader().getResource("game_obj_sound/burstfirewav-14443.wav");
        this.hit = this.getClass().getClassLoader().getResource("game_obj_sound/electronic-impact-hard-10018.wav");
        this.destroy = this.getClass().getClassLoader().getResource("game_obj_sound/duck-quack.wav");
        this.destroyPlayer = this.getClass().getClassLoader().getResource("game_obj_sound/table-smash-47690.wav");

    }
    public void soundShoot(){
        play(shoot);
    }
    public void soundHit(){
        play(hit);
    }
    public void soundDestroy(){
        play(destroy);
    }  public void soundDestroyPlayer(){
        play(destroyPlayer);
    }
    public void play(URL url){
        try{
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if(event.getType()==LineEvent.Type.STOP){
                        clip.close();
                    }
                }
            });
            audioIn.close();
            clip.start();
        }catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println(e);
        }
    }
}
