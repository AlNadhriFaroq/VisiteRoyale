package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PaquetTest {

    @Test
    void testAjouterEtGetCarte() {
        Paquet paquet = new Paquet();
        Assertions.assertTrue(paquet.estVide());
        paquet.defausser(Carte.R1);
        Assertions.assertFalse(paquet.estVide());
        Assertions.assertTrue(paquet.getCarte(Type.ROI, 1).equals(Carte.R1));
    }

    @Test
    void testGetIndiceCarte() {
        Paquet paquet = new Paquet();
        paquet.defausser(Carte.R1);
        Assertions.assertTrue(paquet.getCarte(0).equals(Carte.R1));
    }

    @Test
    void testGetTaille() {
        Paquet paquet = new Paquet();
        paquet.defausser(Carte.R1);
        paquet.defausser(Carte.S1);
        Assertions.assertTrue(paquet.getTaille() == 2);
    }

    @Test
    void testGetNombreTypeCarte() {
        Paquet paquet = new Paquet();
        paquet.defausser(Carte.S3);
        paquet.defausser(Carte.S1);
        Assertions.assertTrue(paquet.getNombreTypeCarte(Type.SOR) == 2);
    }

    @Test
    void testGetNombreCarte() {
        Paquet paquet = new Paquet();
        paquet.defausser(Carte.S3);
        paquet.defausser(Carte.S1);
        Assertions.assertTrue(paquet.getNombreCarte(Type.SOR,1) == 1);
    }

    @Test
    void testPiocher() {
        Paquet paquet = new Paquet();
        paquet.defausser(Carte.S3);
        paquet.defausser(Carte.S1);
        Carte carte1 = paquet.piocher();
        Carte carte2 = paquet.piocher();
        Assertions.assertTrue(paquet.estVide());
        Assertions.assertTrue(carte1 != null && carte2 != null);
    }

    @Test
    void testRemplir() {
        Paquet paquet = new Paquet();
        paquet.remplir();
        Assertions.assertTrue(paquet.getTaille() == 54);
    }

    @Test
    void testTransferer() {
        Paquet paquet = new Paquet();
        paquet.remplir();
        Paquet paquet2 = new Paquet();
        Assertions.assertTrue(!paquet.estVide() && paquet2.estVide());
        paquet2.transferer(paquet);
        Assertions.assertTrue(paquet.estVide() && !paquet2.estVide());
    }

    @Test
    void copier() {
        Paquet paquet = new Paquet();
        paquet.remplir();
        Paquet paquet2 = new Paquet();
        paquet2.copier(paquet);
        Assertions.assertTrue(paquet.equals(paquet2));
    }

    @Test
    void testMelanger() {
        Paquet paquet = new Paquet();
        paquet.remplir();
        Paquet paquet2 = new Paquet();
        paquet2.copier(paquet);
        paquet.melanger();
        Assertions.assertFalse(paquet.equals(paquet2));
    }

    @Test
    void testVider() {
        Paquet paquet = new Paquet();
        paquet.remplir();
        Assertions.assertFalse(paquet.estVide());
        paquet.vider();
        Assertions.assertTrue(paquet.estVide());
    }

    @Test
    void contientCarte() {
        Paquet paquet = new Paquet();
        Assertions.assertFalse(paquet.contientCarte(Carte.F5));
        paquet.remplir();
        Assertions.assertTrue(paquet.contientCarte(Carte.F5));
    }

    @Test
    void testEquals() {
        Paquet paquet1 = new Paquet();
        Paquet paquet2 = new Paquet();
        Assertions.assertTrue(paquet1.equals(paquet2));
        paquet1.remplir();
        paquet2.remplir();
        Assertions.assertFalse(paquet1.equals(paquet2));
    }

    @Test
    void testClone() {
        Paquet paquet1 = new Paquet();
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
