package Global;

import java.util.*;
import java.io.*;

public class Configuration {
    static Configuration instance = null;
    Properties prop;
    final String dossierParametres;

    protected Configuration() {
        dossierParametres = System.getProperty("user.home") + File.separator + "ProjetJeu";
        File dossier = new File(dossierParametres);
        dossier.mkdirs();

        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("defauts.cfg");
        Properties defaut = new Properties();
        try {
            defaut.load(in);
            String nom = dossierParametres + File.separator + "parametres.cfg";
            in = new FileInputStream(nom);
            prop = new Properties(defaut);
            prop.load(in);
        } catch (FileNotFoundException e) {
            prop = defaut;
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    public static Configuration instance() {
        if (instance == null)
            instance = new Configuration();
        return instance;
    }

    public String lire(String nom) {
        String valeur = prop.getProperty(nom);
        if (valeur != null)
            return valeur;
        else
            throw new NoSuchElementException("Propriété " + nom + " manquante !");
    }

    public void ecrire(String nom, String valeur) {
        Object ancienneValeur = prop.setProperty(nom, valeur);
        if (ancienneValeur == null)
            throw new NoSuchElementException("Propriété " + nom + " manquante !");
        else
            sauvegarder();
    }

    public void sauvegarder() {
        try {
            String nom = dossierParametres + File.separator + "parametres.cfg";
            File file = new File(nom);
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            prop.store(out, null);
        } catch (IOException e) {
            System.err.println("Impossible de sauvegarder dans parametres.cfg !");
            System.err.println(e);
            System.exit(1);
        }
    }
}
