package Vue;

import Global.Configuration;

import java.io.File;
import javax.sound.sampled.*;

public class Audio {
    public static final Audio MUSIQUE_MENUS1 = new Audio("Musiques/antiqua.wav");
    public static final Audio MUSIQUE_MENUS2 = new Audio("Musiques/mozart.wav");

    public static final Audio SON_DEFAITE = new Audio("Sons/defaite.wav");
    public static final Audio SON_VICTOIRE = new Audio("Sons/victoire.wav");

    private final Clip clip;

    private Audio(String nom) {
        String dossier = "resources/Audios/";
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(dossier + nom)));
            ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(getVolume());
        } catch (Exception e) {
            throw new RuntimeException("Vue.Audio() : Erreur lors du chargement du son : '" + nom + "'.\n" + e);
        }
    }

    public void jouer() {
        clip.setMicrosecondPosition(0);
        clip.start();
    }

    public void boucler() {
        clip.setMicrosecondPosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void arreter() {
        clip.stop();
    }

    public static int getVolume() {
        return Integer.parseInt(Configuration.instance().lire("Volume"));
    }

    private static void setVolume(int volume) {
        if (volume >= 0 && volume <= 10) {
            Configuration.instance().ecrire("Volume", Integer.toString(volume));
            float decibel = 20f * (float) Math.log10(((float) volume) / 10f);
            ((FloatControl) MUSIQUE_MENUS1.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibel);
            ((FloatControl) MUSIQUE_MENUS2.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibel);
            ((FloatControl) SON_DEFAITE.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibel);
            ((FloatControl) SON_VICTOIRE.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibel);
        } else {
            throw new RuntimeException("Vue.Audio.setVolume() : Volume entré invalide.");
        }
    }

    public static void arreterDemarrer() {
        setVolume(getVolume() == 0 ? 5 : 0);
    }

    public static void diminuerVolume() {
        setVolume(getVolume() - 1);
    }

    public static void augmenterVolume() {
        setVolume(getVolume() + 1);
    }

    public static boolean peutDiminuerVolume() {
        return getVolume() > 0;
    }

    public static boolean peutAugmenterVolume() {
        return getVolume() < 10;
    }
}
