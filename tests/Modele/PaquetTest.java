package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PaquetTest {

    @Test
    void testGetCarte() {
        Paquet paquet = new Paquet(54);
        paquet.inserer(Carte.R1);
        Carte carte = paquet.getCarte(0);
        Assertions.assertEquals(Carte.R1, carte);
    }

    @Test
    void testGetTaille() {
        Paquet paquet = new Paquet(54);
        paquet.inserer(Carte.R1);
        paquet.inserer(Carte.S1);
        Assertions.assertTrue(paquet.getTaille() == 2);
    }

    @Test
    void testGetTailleMax() {
        Paquet paquet = new Paquet(8);
        Assertions.assertTrue(paquet.getTailleMax() == 8);
    }

    @Test
    void testGetNombreTypeCarte() {
        Paquet paquet = new Paquet(54);
        paquet.inserer(Carte.S3);
        paquet.inserer(Carte.S1);
        Assertions.assertTrue(paquet.getNombreTypeCarte(Type.SOR) == 2);
    }

    @Test
    void testGetNombreCarte() {
        Paquet paquet = new Paquet(54);
        paquet.inserer(Carte.S3);
        paquet.inserer(Carte.S1);
        Assertions.assertTrue(paquet.getNombreCarte(Type.SOR,1) == 1);
    }

    @Test
    void testInserer() {
        Paquet paquet = new Paquet(54);
        Assertions.assertTrue(paquet.estVide());
        paquet.inserer(Carte.R1);
        Assertions.assertFalse(paquet.estVide());
        paquet.inserer(Carte.F4);
        paquet.inserer(Carte.G2, true);
        Assertions.assertEquals(Carte.R1, paquet.getCarte(0));
        Assertions.assertEquals(Carte.G2, paquet.getCarte(1));
        Assertions.assertEquals(Carte.F4, paquet.getCarte(2));
    }

    @Test
    void testExtraire() {
        Paquet paquet = new Paquet(54);
        paquet.inserer(Carte.S3);
        paquet.inserer(Carte.S1);
        paquet.inserer(Carte.GC);
        Carte carte2 = paquet.extraire();
        Carte carte1 = paquet.extraire(Carte.S1);
        Carte carte3 = paquet.extraire();
        Assertions.assertTrue(paquet.estVide());
        Assertions.assertTrue(carte1 != null && carte2 != null && carte3 != null);
    }

    @Test
    void testRemplir() {
        Paquet paquet = new Paquet(54);
        paquet.remplir();
        Assertions.assertTrue(paquet.getTaille() == 54);
    }

    @Test
    void testTransferer() {
        Paquet paquet = new Paquet(54);
        paquet.remplir();
        Paquet paquet2 = new Paquet(54);
        Assertions.assertTrue(!paquet.estVide() && paquet2.estVide());
        paquet2.transferer(paquet);
        Assertions.assertTrue(paquet.estVide() && !paquet2.estVide());
    }

    @Test
    void copier() {
        Paquet paquet = new Paquet(54);
        paquet.remplir();
        Paquet paquet2 = new Paquet(54);
        paquet2.copier(paquet);
        Assertions.assertTrue(paquet.equals(paquet2));
    }

    @Test
    void testMelanger() {
        Paquet paquet1 = new Paquet(54);
        paquet1.remplir();
        Paquet paquet2 = new Paquet(54);
        paquet2.copier(paquet1);
        paquet1.melanger();
        Assertions.assertFalse(paquet1.equals(paquet2));
    }

    @Test
    void testTrier() {
        Paquet paquet1 = new Paquet(54);
        paquet1.remplir();
        paquet1.melanger();
        Paquet paquet2 = new Paquet(54);
        paquet2.copier(paquet1);
        paquet2.melanger();
        paquet1.trier();
        Assertions.assertFalse(paquet1.equals(paquet2));
    }

    @Test
    void testVider() {
        Paquet paquet = new Paquet(54);
        paquet.remplir();
        Assertions.assertFalse(paquet.estVide());
        paquet.vider();
        Assertions.assertTrue(paquet.estVide());
    }

    @Test
    void contientCarte() {
        Paquet paquet = new Paquet(54);
        Assertions.assertFalse(paquet.contientCarte(Carte.F5));
        paquet.remplir();
        Assertions.assertTrue(paquet.contientCarte(Carte.F5));
    }

    @Test
    void testEquals() {
        Paquet paquet1 = new Paquet(54);
        Paquet paquet2 = new Paquet(54);
        Assertions.assertTrue(paquet1.equals(paquet2));
        paquet1.remplir();
        paquet2.remplir();
        Assertions.assertFalse(paquet1.equals(paquet2));
    }

    @Test
    void testClone() {
        Paquet paquet1 = new Paquet(54);
        paquet1.remplir();
        Assertions.assertFalse(paquet1.estVide());
        Paquet paquet2 = paquet1.clone();
        Assertions.assertTrue(paquet1.equals(paquet2));
        Carte carte = paquet1.extraire();
        Assertions.assertFalse(paquet1.equals(paquet2));
    }
}
