package Vue;

import Global.Configuration;

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
    int volumeEchelle;
    Float volume;

    public Audio() {
        r = new Random();
        clips = new Clip[4];
        volumeEchelle = Integer.parseInt(Configuration.instance().lire("Volume"));

        String DOSSIER = "resources/Audios/";
        clips[MUSIQUE_MENUS1] = chargerClips(DOSSIER + "Musiques/antiqua.wav");
        clips[MUSIQUE_MENUS2] = chargerClips(DOSSIER + "Musiques/mozart.wav");

        clips[SON_DEFAITE] = chargerClips(DOSSIER + "Sons/defaite.wav");
        clips[SON_VICTOIRE] = chargerClips(DOSSIER + "Sons/victoire.wav");

        reglerVolume();
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

    public void diminuerVolume(){
        volumeEchelle =volumeEchelle <= 0 ? 0: volumeEchelle - 1;
        reglerVolume();
    }

    public void setVolume(int volume){
        if(volumeEchelle == 0 && volume >= 0)
            volumeEchelle = Integer.parseInt(Configuration.instance().lire("Volume"));
        else if(volumeEchelle != 0 && volume == 0)
            volumeEchelle = 0;
        else
            volumeEchelle = volume > 0 && volume <= 5 ? volume : this.volumeEchelle;
        reglerVolume();

    }

    public void augmenterVolume(){
        volumeEchelle = volumeEchelle >= 5 ? 5: volumeEchelle + 1;
        reglerVolume();
    }

    public void reglerVolume(){
       switch (volumeEchelle){
           case 0 : volume = -80f; break;
           case 1 : volume = -20f; break;
           case 2 : volume = -12f; break;
           case 3 : volume = -5f; break;
           case 4 : volume = 1f; break;
           case 5 : volume = 6f; break;
           default: break;
       }
       Configuration.instance().ecrire("Volume",Integer.toString(volumeEchelle));
       for(Clip clip : clips)
           ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(volume);

    }
}
