package Vue;

import java.util.Random;
import java.io.File;
import javax.sound.sampled.*;

public class Audio {
    String[] sons;
    Clip clip;
    Random rand;
    String path;

    public Audio() {
        sons = new String[5];
        path = "resources/Audios/Musiques/";

        sons[0] = path + "intro_antiqua.wav";
        sons[1] = path + "intro_mozart.wav";

        rand = new Random();
        chargerClip();
    }

    void chargerClip() {
        try {
            File file = new File(sons[rand.nextInt(2)]);
            AudioInputStream audi = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audi);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de son : " + e);
        }
    }

    void jouer() {
        clip.start();
    }

    public void boucler() {
        clip.setMicrosecondPosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void arreter() {
        clip.stop();
    }
}
