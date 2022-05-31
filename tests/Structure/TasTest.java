package Structure;

import Structures.Tas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TasTest {

    @Test
    void testEstVide() {
        Tas<String> tas = new Tas<>(true);
        Assertions.assertTrue(tas.estVide());
    }

    @Test
    void testInserer() {
        Tas<String> tas = new Tas<>(true);
        tas.inserer("test", 4);
        Assertions.assertFalse(tas.estVide());
    }

    @Test
    void testGetTaille() {
        Tas<String> tas = new Tas<>(true);
        Assertions.assertEquals(0, tas.getTaille());
        tas.inserer("Test", 4);
        tas.inserer("essais", 6);
        Assertions.assertEquals(2, tas.getTaille());
    }

    @Test
    void testExtraire() {
        Tas<String> tas = new Tas<>(false);
        Tas<String> tas2 = new Tas<>(true);
        tas.inserer("essais", 6);
        tas.inserer("motlepluslong", 13);
        tas.inserer("test", 4);
        tas2.inserer("essais", 6);
        tas2.inserer("motlepluslong", 13);
        tas2.inserer("test", 4);
        Assertions.assertFalse(tas.estVide());
        Assertions.assertEquals(3, tas.getTaille());
        String txt1 = tas.extraire();
        String txt2 = tas.extraire();
        String txt3 = tas.extraire();
        String txt4 = tas2.extraire();
        String txt5 = tas2.extraire();
        String txt6 = tas2.extraire();
        Assertions.assertTrue(tas.estVide());
        Assertions.assertTrue(tas2.estVide());
        Assertions.assertEquals("test", txt1);
        Assertions.assertEquals("essais", txt2);
        Assertions.assertEquals("motlepluslong", txt3);
        Assertions.assertEquals("motlepluslong", txt4);
        Assertions.assertEquals("essais", txt5);
        Assertions.assertEquals("test", txt6);
    }
}
