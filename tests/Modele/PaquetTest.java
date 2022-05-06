package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaquetTest {

    @Test
    void testAjouterEtGetCarte() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        Assertions.assertTrue(paquet.estVide());
        paquet.ajouter(new Carte(Type.ROI, 1));
        Assertions.assertFalse(paquet.estVide());
        Assertions.assertTrue(paquet.getCarte(Type.ROI, 1).equals(new Carte(Type.ROI, 1)));


    }


    @Test
    void testGetIndiceCarte() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        paquet.ajouter(new Carte(Type.ROI, 1));;
        Assertions.assertTrue(paquet.getCarte(0).equals(new Carte(Type.ROI, 1)));
    }

    @Test
    void testGetTaille() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        paquet.ajouter(new Carte(Type.ROI, 1));
        paquet.ajouter(new Carte(Type.SOR, 1));
        Assertions.assertTrue(paquet.getTaille() == 2);
    }


    @Test
    void testGetNombreTypeCarte() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        paquet.ajouter(new Carte(Type.SOR, 3));
        paquet.ajouter(new Carte(Type.SOR, 1));
        Assertions.assertTrue(paquet.getNombreTypeCarte(Type.SOR) ==2 );
    }

    @Test
    void testGetNombreCarte() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        paquet.ajouter(new Carte(Type.SOR, 3));
        paquet.ajouter(new Carte(Type.SOR, 1));
        Assertions.assertTrue(paquet.getNombreCarte(Type.SOR,1) ==1);
    }

    @Test
    void testPiocher() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        paquet.ajouter(new Carte(Type.SOR, 3));
        paquet.ajouter(new Carte(Type.SOR, 1));
        Carte carte1= paquet.piocher();
        Carte carte2= paquet.piocher();
        Assertions.assertTrue(paquet.estVide());
        Assertions.assertTrue(carte1 != null && carte2 != null);

    }

    @Test
    void testDefausser() {
        Paquet paquet = new Paquet(Paquet.NON_ORDONNE);
        paquet.ajouter(new Carte(Type.SOR, 3));
        paquet.ajouter(new Carte(Type.SOR, 1));
        Carte carte1= paquet.defausser(new Carte(Type.SOR, 3));
        Assertions.assertTrue(!paquet.estVide());
        Assertions.assertTrue(carte1.equals(new Carte(Type.SOR, 3)));
    }

    @Test
    void testRemplir() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        Paquet.creerJeuCartes();
        paquet.remplir();
        Assertions.assertTrue(paquet.getTaille() == Paquet.JEU_CARTES.getTaille());
    }

    @Test
    void testTransferer() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        Paquet.creerJeuCartes();
        paquet.remplir();
        Paquet paquet2 = new Paquet(Paquet.ORDONNE);

        Assertions.assertTrue(!paquet.estVide() && paquet2.estVide());
        paquet2.transferer(paquet);

        Assertions.assertTrue(paquet.estVide() && !paquet2.estVide());
    }

    @Test
    void copier() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        Paquet.creerJeuCartes();
        paquet.remplir();
        Paquet paquet2 = new Paquet(Paquet.ORDONNE);
        paquet2.copier(paquet);

        Assertions.assertTrue(paquet.equals(paquet2));
    }

    @Test
    void testMelanger() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        Paquet.creerJeuCartes();
        paquet.remplir();
        Paquet paquet2 = new Paquet(Paquet.ORDONNE);
        paquet2.copier(paquet);
        paquet.melanger();
        Assertions.assertFalse(paquet.equals(paquet2));
    }

    @Test
    void testTrier() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        Paquet.creerJeuCartes();
        paquet.remplir();
        Paquet paquet2 = new Paquet(Paquet.ORDONNE);
        paquet2.copier(paquet);
        paquet.melanger();
        paquet.trier();
        Assertions.assertTrue(paquet.equals(paquet2));
    }

    @Test
    void testVider() {
        Paquet paquet = new Paquet(Paquet.ORDONNE);
        Paquet.creerJeuCartes();
        paquet.remplir();
        Assertions.assertFalse(paquet.estVide());
        paquet.vider();
        Assertions.assertTrue(paquet.estVide());
    }

    @Test
    void contientCarte() {
        Paquet paquet = new Paquet(Paquet.NON_ORDONNE);
        Paquet.creerJeuCartes();
        paquet.remplir();
        Assertions.assertTrue(paquet.contientCarte(new Carte(Type.FOU,5)));
        Carte carte = paquet.defausser(new Carte(Type.FOU,5));
        Assertions.assertFalse(paquet.contientCarte(new Carte(Type.FOU,5)));
    }


    @Test
    void testEquals() {
        Paquet paquet1 = new Paquet(Paquet.ORDONNE);
        Paquet paquet2 = new Paquet(Paquet.ORDONNE);
        Paquet.creerJeuCartes();
        paquet1.remplir();
        paquet2.remplir();

        Assertions.assertTrue(paquet1.equals(paquet2));
        Carte carte = paquet1.piocher();
        Assertions.assertFalse(paquet1.equals(paquet2));

    }

    @Test
    void testClone() {
        Paquet paquet1 = new Paquet(Paquet.ORDONNE);
        Paquet.creerJeuCartes();
        paquet1.remplir();

        Assertions.assertFalse(paquet1.estVide());

        Paquet paquet2 = paquet1.clone();
        Assertions.assertTrue(paquet1.equals(paquet2));

        Carte carte = paquet1.piocher();
        Assertions.assertFalse(paquet1.equals(paquet2));

    }

    @Test
    void testToString() {
    }
}