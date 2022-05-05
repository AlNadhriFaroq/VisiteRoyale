package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteTest {

    @Test
    void testGetType() {
        Carte carte = new Carte(new Type(2),1);
        Assertions.assertEquals(2,carte.getType());
    }

    @Test
    void testGetDeplacement() {
        Carte carte = new Carte(new Type(4),5);
        Assertions.assertEquals(5,carte.getDeplacement());
    }

    @Test
    void testEstType() {
        Carte carte = new Carte(new Type(4),5);
        Assertions.assertTrue(carte.estType(4));
    }

    @Test
    void compareTo() {
        Carte carte1 = new Carte(new Type(4),5);
        Carte carte2 = new Carte(new Type(4),5);
        Assertions.assertEquals(0,carte1.compareTo(carte2));
        Carte carte3 = new Carte(new Type(3),5);
        Carte carte4 = new Carte(new Type(4),5);
        Assertions.assertEquals(-1,carte3.compareTo(carte4));
        Carte carte5 = new Carte(new Type(4),5);
        Carte carte6 = new Carte(new Type(3),5);
        Assertions.assertEquals(1,carte5.compareTo(carte6));
    }

    @Test
    void compareToMemeTypeDiffDep() {
        Carte carte1 = new Carte(new Type(4),3);
        Carte carte2 = new Carte(new Type(4),5);
        Assertions.assertEquals(-1,carte1.compareTo(carte2));
        Carte carte3 = new Carte(new Type(4),5);
        Carte carte4 = new Carte(new Type(4),3);
        Assertions.assertEquals(1,carte3.compareTo(carte4));
    }

    @Test
    void testEquals() {
        Carte carte1 = new Carte(new Type(4),5);
        Carte carte2 = new Carte(new Type(4),5);
        Assertions.assertTrue(carte1.equals(carte2));

        Carte carte3 = new Carte(new Type(4),2);
        Assertions.assertFalse(carte1.equals(carte3));
    }

    @Test
    void testClone() {
        Carte carte1 = new Carte(new Type(4),5);
        Carte carte2 = carte1.clone();
        Assertions.assertTrue(carte1.equals(carte2));
    }
}