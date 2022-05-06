package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteTest {

    @Test
    void testGetType() {
        Carte carte = new Carte(Type.FOU,1);
        Assertions.assertEquals(Type.FOU,carte.getType());
    }

    @Test
    void testGetDeplacement() {
        Carte carte = new Carte(Type.FOU,1);
        Assertions.assertEquals(1,carte.getDeplacement());
    }

    @Test
    void testEstType() {
        Carte carte = new Carte(Type.ROI,5);
        Assertions.assertTrue(carte.estType(Type.ROI));
    }

    @Test
    void compareTo() {
        Carte carte1 = new Carte(Type.ROI,5);
        Carte carte2 = new Carte(Type.ROI,5);
        Assertions.assertEquals(0,carte1.compareTo(carte2));
        Carte carte3 = new Carte(Type.SOR,5);
        Carte carte4 = new Carte(Type.FOU,5);
        Assertions.assertTrue(carte3.compareTo(carte4) < 0);
        Carte carte5 = new Carte(Type.FOU,5);
        Carte carte6 = new Carte(Type.SOR,5);
        Assertions.assertTrue(carte5.compareTo(carte6) > 0);
    }

    @Test
    void compareToMemeTypeDiffDep() {
        Carte carte1 = new Carte(Type.FOU,3);
        Carte carte2 = new Carte(Type.FOU,5);
        Assertions.assertTrue(carte1.compareTo(carte2) < 0);
        Carte carte3 = new Carte(Type.FOU,5);
        Carte carte4 = new Carte(Type.FOU,3);
        Assertions.assertTrue(carte3.compareTo(carte4) > 0);
    }


    @Test
    void testEquals() {
        Carte carte1 = new Carte(Type.FOU,5);
        Carte carte2 = new Carte(Type.FOU,5);
        Assertions.assertTrue(carte1.equals(carte2));

        Carte carte3 = new Carte(Type.FOU,2);
        Assertions.assertFalse(carte1.equals(carte3));
    }

    @Test
    void testClone() {
        Carte carte1 = new Carte(Type.FOU,5);
        Carte carte2 = carte1.clone();
        Assertions.assertTrue(carte1.equals(carte2));
    }
}