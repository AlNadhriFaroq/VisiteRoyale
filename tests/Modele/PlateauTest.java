package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlateauTest {

    @Test
    void testEvaluerDeplacementCouronneVrt() {
        Plateau p = new Plateau();
        Assertions.assertEquals(0, p.evaluerDeplacementCouronneVrt());
        p.setPositionPion(Pion.GAR_VRT, Plateau.CHATEAU_VRT);
        Assertions.assertEquals(1, p.evaluerDeplacementCouronneVrt());
        p.setPositionPion(Pion.FOU, Plateau.CHATEAU_VRT);
        Assertions.assertEquals(2, p.evaluerDeplacementCouronneVrt());
    }

    @Test
    void testEvaluerDeplacementCouronneRge() {
        Plateau p = new Plateau();
        Assertions.assertEquals(0, p.evaluerDeplacementCouronneRge());
        p.setPositionPion(Pion.GAR_RGE, Plateau.CHATEAU_RGE);
        Assertions.assertEquals(1, p.evaluerDeplacementCouronneRge());
        p.setPositionPion(Pion.FOU, Plateau.CHATEAU_RGE);
        Assertions.assertEquals(2, p.evaluerDeplacementCouronneRge());
    }

    @Test
    void pionROIestDeplacable() {
        Plateau p = new Plateau();
        Assertions.assertTrue(p.pionEstDeplacable(Pion.ROI, Plateau.FONTAINE + 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.ROI, Plateau.FONTAINE - 1));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI, Plateau.FONTAINE + 2));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI, Plateau.FONTAINE - 2));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI, Plateau.FONTAINE + 4));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI, Plateau.FONTAINE - 4));
    }

    @Test
    void pionGAR_VRTestDeplacable() {
        Plateau p = new Plateau();
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT, Plateau.FONTAINE + 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_VRT, Plateau.FONTAINE - 1));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT, Plateau.FONTAINE + 2));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_VRT, Plateau.FONTAINE - 2));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT, Plateau.FONTAINE + 4));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_VRT, Plateau.FONTAINE - 4));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT, Plateau.BORDURE_VRT - 5));
    }

    @Test
    void pionGAR_RGEestDeplacable() {
        Plateau p = new Plateau();
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_RGE, Plateau.FONTAINE - 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_RGE, Plateau.FONTAINE + 1));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_RGE, Plateau.FONTAINE - 2));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_RGE, Plateau.FONTAINE + 2));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_RGE, Plateau.FONTAINE - 4));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_RGE, Plateau.FONTAINE + 4));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_RGE, Plateau.CHATEAU_RGE + 1));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_RGE, Plateau.CHATEAU_RGE + 2));
    }

    @Test
    void pionSORetFOUestDeplacable() {
        Plateau p = new Plateau();
        Assertions.assertTrue(p.pionEstDeplacable(Pion.SOR, Plateau.FONTAINE - 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.SOR, Plateau.FONTAINE + 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.SOR, Plateau.FONTAINE - 4));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.SOR, Plateau.FONTAINE + 4));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.SOR, Plateau.BORDURE_VRT - 4));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.SOR, Plateau.BORDURE_RGE + 4));

        Assertions.assertTrue(p.pionEstDeplacable(Pion.FOU, Plateau.FONTAINE - 2));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.FOU, Plateau.FONTAINE + 2));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.FOU, Plateau.BORDURE_VRT - 4));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.FOU, Plateau.BORDURE_RGE + 4));
    }

    @Test
    void couronneEstDeplacable() {
        Plateau p = new Plateau();
        Assertions.assertTrue(p.couronneEstDeplacable(Plateau.FONTAINE - 1));
        Assertions.assertTrue(p.couronneEstDeplacable(Plateau.FONTAINE + 1));
        Assertions.assertTrue(p.couronneEstDeplacable(Plateau.FONTAINE - 3));
        Assertions.assertTrue(p.couronneEstDeplacable(Plateau.FONTAINE + 3));
        Assertions.assertFalse(p.couronneEstDeplacable(Plateau.BORDURE_RGE + 3));
        Assertions.assertFalse(p.couronneEstDeplacable(Plateau.BORDURE_VRT - 3));
    }

    @Test
    void pionDansDucheVrt() {
    }

    @Test
    void pionDansDucheRge() {
    }

    @Test
    void pionDansChateauVrt() {
    }

    @Test
    void pionDansChateauRge() {
    }

    @Test
    void couronneDansChateauVrt() {
    }

    @Test
    void couronneDansChateauRge() {
    }

    @Test
    void pionDansFontaine() {
    }

    @Test
    void peutUtiliserPouvoirSor() {
    }

    @Test
    void vrtPeutUtiliserPouvoirFou() {
    }

    @Test
    void rgePeutUtiliserPouvoirFou() {
    }

    @Test
    void peutUtiliserPrivilegeRoi() {
    }

    @Test
    void estTerminee() {
        Plateau p1 = new Plateau();
        p1.setPositionPion(Pion.ROI, Plateau.CHATEAU_RGE);
        Assertions.assertTrue(p1.estTerminee());

        Plateau p2 = new Plateau();
        p2.setPositionCouronne(Plateau.CHATEAU_RGE);
        Assertions.assertTrue(p2.estTerminee());
    }

    @Test
    void testEquals() {
        Plateau p1 = new Plateau();
        Plateau p2 = new Plateau();
        Plateau p3 = new Plateau();
        p3.setPositionPion(Pion.GAR_RGE, 14);
        Assertions.assertTrue(p1.equals(p2));
        Assertions.assertFalse(p1.equals(p3));
    }

    @Test
    void testClone() {
        Plateau p1 = new Plateau();
        Plateau p2 = p1.clone();
        Assertions.assertEquals(p1, p2);
        p2.setPositionPion(Pion.ROI, Plateau.FONTAINE + 1);
        Assertions.assertNotEquals(p1, p2);
    }
}