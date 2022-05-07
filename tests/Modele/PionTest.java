package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PionTest {

    @Test
    void testGetType() {
        Pion p = Pion.ROI;
        Assertions.assertEquals(Type.ROI, p.getType());
        Assertions.assertNotEquals(Type.FOU, p.getType());
    }

    @Test
    void testEquals() {
        Pion p1 = Pion.ROI;
        Pion p2 = Pion.ROI;
        Pion p3 = Pion.SOR;
        Assertions.assertTrue(p1.equals(p2));
        Assertions.assertFalse(p1.equals(p3));
    }

    @Test
    void testClone() {
        Pion p1 = Pion.ROI;
        Pion p2 = p1.clone();
        Assertions.assertTrue(p1.equals(p2));
    }
}