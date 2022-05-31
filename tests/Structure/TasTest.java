package Structure;

import Structures.Tas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TasTest {

    @Test
    void testEstVide() {
        Tas<String> tas = new Tas<>();
        Assertions.assertTrue(tas.estVide());
    }

    @Test
    void testInserer() {
        Tas<String> tas = new Tas<>();
        tas.inserer("test", 4);
        Assertions.assertFalse(tas.estVide());
    }

    @Test
    void testGetTaille() {
        Tas<String> tas = new Tas<>();
        Assertions.assertEquals(0, tas.getTaille());
        tas.inserer("Test", 4);
        tas.inserer("essais", 6);
        Assertions.assertEquals(2, tas.getTaille());
    }

    @Test
    void testExtraire() {
        Tas<String> tas = new Tas<>();
        tas.inserer("essais", 6);
        tas.inserer("motlepluslong", 13);
        tas.inserer("test", 4);
        Assertions.assertFalse(tas.estVide());
        Assertions.assertEquals(3, tas.getTaille());
        String txt1 = tas.extraire();
        String txt2 = tas.extraire();
        String txt3 = tas.extraire();
        Assertions.assertTrue(tas.estVide());
        Assertions.assertEquals("motlepluslong", txt1);
        Assertions.assertEquals("essais", txt2);
        Assertions.assertEquals("test", txt3);
    }
}
