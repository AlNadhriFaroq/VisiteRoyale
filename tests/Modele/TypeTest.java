package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TypeTest {

    @Test
    void testTexteEnType() {
        Type type;
        type = Type.texteEnType("R");
        Assertions.assertEquals(Type.ROI, type);
        type = Type.texteEnType("G");
        Assertions.assertEquals(Type.GAR, type);
        type = Type.texteEnType("S");
        Assertions.assertEquals(Type.SOR, type);
        type = Type.texteEnType("F");
        Assertions.assertEquals(Type.FOU, type);
    }

    @Test
    void testCompareTo() {
        Type t1 = Type.SOR;
        Type t2 = Type.SOR;
        Assertions.assertEquals(0, t1.compareTo(t2));
        t1 = Type.FOU;
        Assertions.assertTrue(t1.compareTo(t2) > 0);
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
        Assertions.assertEquals(t1, t2);
        t2 = Type.ROI;
        Assertions.assertNotEquals(t1, t2);
    }
}