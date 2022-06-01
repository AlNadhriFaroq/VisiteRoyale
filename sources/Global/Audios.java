package Global;

import java.io.BufferedInputStream;
import java.util.Objects;
import javax.sound.sampled.*;

public class Audios {
    public static final boolean MUSIQUE = true;
    public static final boolean SONS = false;

    public static final String[] MUSIQUES = {"Michael Praetorius - Bransle de la Torche", "Mozart - Butterflies"};
    public static Audios MUSIQUE_MENU;
    public static Audios MUSIQUE_JEU;
    public static final Audios MUSIQUE_MENUS1 = new Audios("BransleDeLaTorche", MUSIQUE);
    public static final Audios MUSIQUE_MENUS2 = new Audios("Butterflies", MUSIQUE);
    public static final Audios MUSIQUE_JEU1 = new Audios("PippinTheHunchback", MUSIQUE);
    public static final Audios MUSIQUE_JEU2 = new Audios("LaFestaDeComiat", MUSIQUE);

    public static final Audios SON_DEFAITE = new Audios("Defaite", SONS);
    public static final Audios SON_VICTOIRE = new Audios("Victoire", SONS);

    private final Clip clip;

    private Audios(String nom, boolean typeAudio) {
        String chemin = "/Audios/" + (typeAudio == MUSIQUE ? "Musiques/" : "Sons/") + nom + ".wav";
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(Audios.class.getResourceAsStream(chemin)))));
            float decibel = 20f * (float) Math.log10(((float) getVolume(typeAudio)) / 10f);
            ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibel);
        } catch (Exception e) {
            throw new RuntimeException("Global.Audio() : Erreur lors du chargement du son : '" + chemin + "'.\n" + e);
        }
    }

    public void jouer() {
        clip.setMicrosecondPosition(0);
        clip.start();
    }

    public void reinit() {
        clip.setMicrosecondPosition(0);
    }

    public void boucler() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void arreter() {
        clip.stop();
    }

    public static int getVolume(boolean typeAudio) {
        return Integer.parseInt(Configuration.instance().lire(typeAudio == MUSIQUE ? "VolumeMusique" : "VolumeSons"));
    }

    public static void setMusiqueMenu(String nom) {
        Configuration.instance().ecrire("Musique", nom);
        switch (nom) {
            case "Michael Praetorius - Bransle de la Torche":
                MUSIQUE_MENU = MUSIQUE_MENUS1;
                break;
            case "Mozart - Butterflies":
                MUSIQUE_MENU = MUSIQUE_MENUS2;
                break;
            default:
                MUSIQUE_MENU = MUSIQUE_MENUS1;
        }
    }

    public static void setVolume(int volume, boolean typeAudio) {
        if (volume >= 0 && volume <= 10) {
            float decibel = 20f * (float) Math.log10(((float) volume) / 10f);
            if (typeAudio == MUSIQUE) {
                Configuration.instance().ecrire("VolumeMusique", Integer.toString(volume));
                ((FloatControl) MUSIQUE_MENUS1.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibel);
                ((FloatControl) MUSIQUE_MENUS2.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibel);
                ((FloatControl) MUSIQUE_JEU1.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibel);
                ((FloatControl) MUSIQUE_JEU2.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibel);
            } else {
                Configuration.instance().ecrire("VolumeSons", Integer.toString(volume));
                ((FloatControl) SON_DEFAITE.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibel);
                ((FloatControl) SON_VICTOIRE.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibel);
            }
        } else {
            throw new RuntimeException("Global.Audio.setVolume() : Volume entré invalide.");
        }
    }

    public static void arreterDemarrer(boolean typeAudio) {
        setVolume(getVolume(typeAudio) == 0 ? 5 : 0, typeAudio);
    }

    public static void diminuerVolume(boolean typeAudio) {
        setVolume(getVolume(typeAudio) - 1, typeAudio);
    }

    public static void augmenterVolume(boolean typeAudio) {
        setVolume(getVolume(typeAudio) + 1, typeAudio);
    }

    public static boolean peutDiminuerVolume(boolean typeAudio) {
        return getVolume(typeAudio) > 0;
    }

    public static boolean peutAugmenterVolume(boolean typeAudio) {
        return getVolume(typeAudio) < 10;
    }
}
