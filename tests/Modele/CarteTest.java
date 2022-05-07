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
    void compareTo() {
        Carte carte1 = Carte.R1;
        Carte carte2 = Carte.R1;
        Assertions.assertEquals(0, carte1.compareTo(carte2));
        Carte carte3 = Carte.S3;
        Carte carte4 = Carte.F5;
        Assertions.assertTrue(carte3.compareTo(carte4) < 0);
        Carte carte5 = Carte.F5;
        Carte carte6 = Carte.S2;
        Assertions.assertTrue(carte5.compareTo(carte6) > 0);
    }

    @Test
    void compareToMemeTypeDiffDep() {
        Carte carte1 = Carte.F3;
        Carte carte2 = Carte.F5;
        Assertions.assertTrue(carte1.compareTo(carte2) < 0);
        Carte carte3 = Carte.F5;
        Carte carte4 = Carte.F3;
        Assertions.assertTrue(carte3.compareTo(carte4) > 0);
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