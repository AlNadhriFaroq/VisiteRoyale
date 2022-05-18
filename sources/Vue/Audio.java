package Vue;

import java.util.Random;
import java.io.File;
import javax.sound.sampled.*;

public class Audio {
    public static final int MUSIQUE_MENUS1 = 0;
    public static final int MUSIQUE_MENUS2 = 1;

    public static final int SON_DEFAITE = 2;
    public static final int SON_VICTOIRE = 3;

    Clip[] clips;
    Random r;

    public Audio() {
        r = new Random();
        clips = new Clip[4];

        String DOSSIER = "resources/Audios/";
        clips[MUSIQUE_MENUS1] = chargerClips(DOSSIER + "Musiques/antiqua.wav");
        clips[MUSIQUE_MENUS2] = chargerClips(DOSSIER + "Musiques/mozart.wav");

        clips[SON_DEFAITE] = chargerClips(DOSSIER + "Sons/defaite.wav");
        clips[SON_VICTOIRE] = chargerClips(DOSSIER + "Sons/victoire.wav");
    }

    private Clip chargerClips(String nom) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(nom));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            return clip;
        } catch (Exception e) {
            throw new RuntimeException("Vue.Audio.chargerClips() : Erreur lors du chargement du son : '" + nom + "'.\n" + e);
        }
    }

    public void jouer(int clip) {
        clips[clip].setMicrosecondPosition(0);
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
