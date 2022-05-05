package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    void testGetType() {
        Type t = new Type(3);
        Assertions.assertEquals(3, t.getType());
    }

    @Test
    void testSetType() {
        Type t = new Type(3);
        t.setType(4);
        Assertions.assertEquals(4, t.getType());
    }

    @Test
    void testEstType() {
        Type t = new Type(3);
        t.setType(4);
        Assertions.assertTrue(t.estType(4));
    }


    @Test
    void compareTo() {
        Type t1 = new Type(3);
        Type t2 = new Type(3);
        Assertions.assertEquals(0,t1.compareTo(t2));
        t1.setType(2);
        Assertions.assertEquals(-1,t1.compareTo(t2));
        t1.setType(4);
        Assertions.assertEquals(1,t1.compareTo(t2));
    }

    @Test
    void testEquals() {
        Type t1 = new Type(3);
        Type t2 = new Type(3);
        Assertions.assertTrue(t1.equals(t2));
    }

    @Test
    void testClone() {
        Type t1 = new Type(3);
        Type t2 = t1.clone();
        Assertions.assertTrue(t1.equals(t2));
        t2.setType(4);
        Assertions.assertFalse(t1.equals(t2));
    }
}