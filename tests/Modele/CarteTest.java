package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CarteTest {

    @Test
    void testGetType() {
        Carte carte1 = Carte.F1;
        Assertions.assertEquals(Type.FOU, carte1.getType());
        Assertions.assertNotEquals(Type.ROI, carte1.getType());
        Carte carte2 = Carte.GC;
        Assertions.assertEquals(Type.GAR, carte2.getType());
    }

    @Test
    void testGetDeplacement() {
        Carte carte1 = Carte.F1;
        Assertions.assertEquals(1, carte1.getDeplacement());
        Carte carte2 = Carte.FM;
        Assertions.assertEquals(6, carte2.getDeplacement());
    }

    @Test
    void testEstDeplacementGar1Plus1() {
        Carte carte1 = Carte.G2;
        Carte carte2 = Carte.G1;
        Assertions.assertTrue(carte1.estDeplacementGar1Plus1());
        Assertions.assertFalse(carte2.estDeplacementGar1Plus1());
    }

    @Test
    void testEstDeplacementGarCentre() {
        Carte carte1 = Carte.GC;
        Carte carte2 = Carte.S3;
        Assertions.assertTrue(carte1.estDeplacementGarCentre());
        Assertions.assertFalse(carte2.estDeplacementGarCentre());
    }

    @Test
    void testEstDeplacementFouCentre() {
        Carte carte1 = Carte.FM;
        Carte carte2 = Carte.F1;
        Assertions.assertTrue(carte1.estDeplacementFouCentre());
        Assertions.assertFalse(carte2.estDeplacementFouCentre());
    }

    @Test
    void testTexteEnCarte() {
        Carte carte;
        carte = Carte.texteEnCarte("R1");
        Assertions.assertEquals(Carte.R1, carte);
        carte = Carte.texteEnCarte("G2");
        Assertions.assertEquals(Carte.G2, carte);
        carte = Carte.texteEnCarte("FM");
        Assertions.assertEquals(Carte.FM, carte);
    }

    @Test
    void testCompareTo() {
        Carte carte1, carte2;
        carte1 = Carte.R1;
        carte2 = Carte.R1;
        Assertions.assertEquals(0, carte1.compareTo(carte2));
        carte1 = Carte.S3;
        carte2 = Carte.F5;
        Assertions.assertTrue(carte1.compareTo(carte2) < 0);
        carte1 = Carte.F5;
        carte2 = Carte.S2;
        Assertions.assertTrue(carte1.compareTo(carte2) > 0);
        carte1 = Carte.F3;
        carte2 = Carte.F5;
        Assertions.assertTrue(carte1.compareTo(carte2) < 0);
        carte1 = Carte.F5;
        carte2 = Carte.F3;
        Assertions.assertTrue(carte1.compareTo(carte2) > 0);
    }

    @Test
    void testEquals() {
        Carte carte1 = Carte.F5;
        Carte carte2 = Carte.F5;
        Carte carte3 = Carte.F2;
        Assertions.assertTrue(carte1.equals(carte2));
        Assertions.assertFalse(carte1.equals(carte3));
    }

    @Test
    void testClone() {
        Carte carte1 = Carte.F5;
        Carte carte2 = carte1.clone();
        Assertions.assertTrue(carte1.equals(carte2));
    }
}