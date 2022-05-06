package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PionTest {

    @Test
    void testGetType() {
        Pion p = new Pion(Type.ROI, 6);
        Assertions.assertEquals(Type.ROI,p.getType());
    }

    @Test
    void testGetPosition() {
        Pion p = new Pion(Type.ROI, 6);
        Assertions.assertEquals(6,p.getPosition());
    }

    @Test
    void testSetPosition() {
        Pion p = new Pion(Type.ROI, 6);
        p.setPosition(12);
        Assertions.assertEquals(12,p.getPosition());
        p.setPosition(16);
        Assertions.assertEquals(16,p.getPosition());
        p.setPosition(10);
        Assertions.assertEquals(10,p.getPosition());
    }

    @Test
    void testEquals() {
        Pion p1 = new Pion(Type.ROI, 6);
        Pion p2 = new Pion(Type.ROI, 6);
        Assertions.assertTrue(p1.equals(p2));
    }


    @Test
    void testClone() {
        Pion p1 = new Pion(Type.ROI, 6);
        Pion p2 = p1.clone();
        Assertions.assertTrue(p1.equals(p2));
    }
}