package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JetonTest {

    @Test
    void testGetFace() {
        Jeton j = new Jeton( 5);
        Assertions.assertFalse(j.getFace());
    }

    @Test
    void testGetPosition() {
        Jeton j = new Jeton( 5);
        Assertions.assertEquals(5,j.getPosition());
    }

    @Test
    void testSetFace() {
        Jeton j = new Jeton( 5);
        j.setFace(true);
        Assertions.assertTrue(j.getFace());
        j.setFace(true);
        Assertions.assertTrue(j.getFace());
        j.setFace(false);
        Assertions.assertFalse(j.getFace());
        j.setFace(false);
        Assertions.assertFalse(j.getFace());
    }

    @Test
    void testSetPosition() {
        Jeton j = new Jeton( 5);
        j.setPosition(8);
        Assertions.assertEquals(8,j.getPosition());
        j.setPosition(8);
        Assertions.assertEquals(8,j.getPosition());
    }


    @Test
    void testAlternerFace() {
        Jeton j = new Jeton( 5);
        j.tournerFace();
        Assertions.assertTrue(j.getFace());
        j.tournerFace();
        Assertions.assertFalse(j.getFace());
    }

    @Test
    void testEquals() {
        Jeton j1 = new Jeton( 5);
        Jeton j2 = new Jeton( 5);
        Assertions.assertTrue(j1.equals(j2));
        j2.setPosition(7);
        Assertions.assertFalse(j1.equals(j2));

    }


    @Test
    void testClone() {
        Jeton j1 = new Jeton( 5);
        Jeton j2 = j1.clone();
        Assertions.assertTrue(j1.equals(j2));
        j2.setPosition(7);
        Assertions.assertFalse(j1.equals(j2));
    }

}