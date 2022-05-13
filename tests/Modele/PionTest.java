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
    void testTexteEnPion() {
        Pion pion;
        pion = Pion.texteEnPion("R");
        Assertions.assertEquals(Pion.ROI, pion);
        pion = Pion.texteEnPion("GV");
        Assertions.assertEquals(Pion.GAR_VRT, pion);
        pion = Pion.texteEnPion("GR");
        Assertions.assertEquals(Pion.GAR_RGE, pion);
        pion = Pion.texteEnPion("S");
        Assertions.assertEquals(Pion.SOR, pion);
        pion = Pion.texteEnPion("F");
        Assertions.assertEquals(Pion.FOU, pion);
    }

    @Test
    void testValeurEnPion() {
        Pion pion;
        pion = Pion.valeurEnPion(0);
        Assertions.assertEquals(Pion.ROI, pion);
        pion = Pion.valeurEnPion(1);
        Assertions.assertEquals(Pion.GAR_VRT, pion);
        pion = Pion.valeurEnPion(2);
        Assertions.assertEquals(Pion.GAR_RGE, pion);
        pion = Pion.valeurEnPion(3);
        Assertions.assertEquals(Pion.SOR, pion);
        pion = Pion.valeurEnPion(4);
        Assertions.assertEquals(Pion.FOU, pion);
    }

    @Test
    void testTypeEnPion() {
        Pion pion;
        pion = Pion.typeEnPion(Type.ROI);
        Assertions.assertEquals(Pion.ROI, pion);
        pion = Pion.typeEnPion(Type.SOR);
        Assertions.assertEquals(Pion.SOR, pion);
        pion = Pion.typeEnPion(Type.FOU);
        Assertions.assertEquals(Pion.FOU, pion);
    }

    @Test
    void testEquals() {
        Pion p1 = Pion.ROI;
        Pion p2 = Pion.ROI;
        Pion p3 = Pion.SOR;
        Assertions.assertEquals(p1, p2);
        Assertions.assertNotEquals(p1, p3);
    }

    @Test
    void testClone() {
        Pion p1 = Pion.ROI;
        Pion p2 = p1.clone();
        Assertions.assertEquals(p1, p2);

    }
}