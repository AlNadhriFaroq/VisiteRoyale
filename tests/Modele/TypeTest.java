package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TypeTest {

    @Test
    void compareTo() {
        Type t1 = Type.SOR;
        Type t2 = Type.SOR;
        Assertions.assertEquals(0, t1.compareTo(t2));
        t1 = Type.FOU;
        Assertions.assertTrue(t1.compareTo(t2) > 0 );
        t1 = Type.ROI;
        Assertions.assertTrue(t1.compareTo(t2) < 0);
    }

    @Test
    void testEquals() {
        Type t1 = Type.SOR;
        Type t2 = Type.SOR;
        Type t3 = Type.FOU;
        Assertions.assertTrue(t1.equals(t2));
        Assertions.assertFalse(t1.equals(t3));
    }

    @Test
    void testClone() {
        Type t1 = Type.SOR;
        Type t2 = t1.clone();
        Assertions.assertTrue(t1.equals(t2));
        t2 = Type.ROI;
        Assertions.assertFalse(t1.equals(t2));
    }
}