package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    @Test
    void testGetType() {
        Type t = new Type(Type.FOU);
        Assertions.assertEquals(Type.FOU, t.getValeur());
    }

    @Test
    void testSetType() {
        Type t = new Type(Type.FOU);
        t.setValeur(Type.ROI);
        Assertions.assertEquals(Type.ROI, t.getValeur());
    }


    @Test
    void testEstType() {
        Type t = new Type(Type.ROI);
        t.setValeur(Type.SOR);
        Assertions.assertTrue(t.estValeur(Type.SOR));
    }


    @Test
    void compareTo() {
        Type t1 = new Type(Type.SOR);
        Type t2 = new Type(Type.SOR);
        Assertions.assertEquals(0,t1.compareTo(t2));
        t1.setValeur(Type.FOU);
        Assertions.assertTrue(t1.compareTo(t2) > 0 );
        t1.setValeur(Type.ROI);
        Assertions.assertTrue(t1.compareTo(t2) < 0);
    }

    @Test
    void testEquals() {
        Type t1 = new Type(Type.SOR);
        Type t2 = new Type(Type.SOR);
        Assertions.assertTrue(t1.equals(t2));
    }

    @Test
    void testClone() {
        Type t1 = new Type(Type.SOR);
        Type t2 = t1.clone();
        Assertions.assertTrue(t1.equals(t2));
        t2.setValeur(Type.ROI);
        Assertions.assertFalse(t1.equals(t2));
    }
}