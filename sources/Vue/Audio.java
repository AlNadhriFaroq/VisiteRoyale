package Vue;

import java.util.Random;
import java.io.File;
import javax.sound.sampled.*;

public class Audio {
    public static final int SON_INTRO1 = 0;
    public static final int SON_INTRO2 = 1;
    public static final int SON_PERTE = 2;
    public static final int SON_VICTOIRE1 = 3;
    public static final int SON_VICTOIRE2 = 4;

    int nombreSons;
    String[] sons;
    Clip[] clips;
    Random rand;
    String path;

    public Audio() {
        nombreSons = 5;
        sons = new String[nombreSons];
        clips = new Clip[nombreSons];
        rand = new Random();

        path = "resources/Audios/";

        sons[0] = path + "Musiques/intro_antiqua.wav";
        sons[1] = path + "Musiques/intro_mozart.wav";

        sons[2] = path + "Sons/lost.wav";
        sons[3] = path + "Sons/tada.wav";
        sons[4] = path + "Sons/medieval_fanfare.wav";

        chargerClips();
    }

    void chargerClips() {
        try {
            for(int i = 0; i < nombreSons; i++) {
                File file = new File(sons[i]);
                AudioInputStream audi = AudioSystem.getAudioInputStream(file);
                clips[i] = AudioSystem.getClip();
                clips[i].open(audi);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de son : " + e);
        }
    }

    public void jouer(int clip) {
        clips[clip].start();
    }

    public void boucler(int clip) {
        clips[clip].setMicrosecondPosition(0);
        clips[clip].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void arreter(int clip) {
        clips[clip].stop();
    }
}
