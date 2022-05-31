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
        Tas<String> tasMin = new Tas<>(false);
        Tas<String> tasMax = new Tas<>(true);
        tasMin.inserer("essais", 6);
        tasMin.inserer("motlepluslong", 13);
        tasMin.inserer("test", 4);
        tasMax.inserer("essais", 6);
        tasMax.inserer("motlepluslong", 13);
        tasMax.inserer("test", 4);
        Assertions.assertFalse(tasMin.estVide());
        Assertions.assertFalse(tasMax.estVide());
        Assertions.assertEquals(3, tasMin.getTaille());
        Assertions.assertEquals(3, tasMax.getTaille());
        String txt1 = tasMin.extraire();
        String txt2 = tasMin.extraire();
        String txt3 = tasMin.extraire();
        String txt4 = tasMax.extraire();
        String txt5 = tasMax.extraire();
        String txt6 = tasMax.extraire();
        Assertions.assertTrue(tasMin.estVide());
        Assertions.assertTrue(tasMax.estVide());
        Assertions.assertEquals("test", txt1);
        Assertions.assertEquals("essais", txt2);
        Assertions.assertEquals("motlepluslong", txt3);
        Assertions.assertEquals("motlepluslong", txt4);
        Assertions.assertEquals("essais", txt5);
        Assertions.assertEquals("test", txt6);
    }
}
