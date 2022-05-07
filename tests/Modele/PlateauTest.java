package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlateauTest {

    @Test
    void testEvaluerDeplacementCouronneVrt() {
        Plateau p = new Plateau(Plateau.DIRECTION_VRT);
        Assertions.assertTrue(p.evaluerDeplacementCouronneVrt() == 0);
        p.setPositionPion(Pion.GAR_VRT, Plateau.CHATEAU_VRT);
        Assertions.assertTrue(p.evaluerDeplacementCouronneVrt() == 1);
        p.setPositionPion(Pion.FOU, Plateau.CHATEAU_VRT);
        Assertions.assertTrue(p.evaluerDeplacementCouronneVrt() == 2);
    }

    @Test
    void testEvaluerDeplacementCouronneRge() {
        Plateau p = new Plateau(Plateau.DIRECTION_RGE);
        Assertions.assertTrue(p.evaluerDeplacementCouronneRge() == 0);
        p.setPositionPion(Pion.GAR_RGE, Plateau.CHATEAU_RGE);
        Assertions.assertTrue(p.evaluerDeplacementCouronneRge() == 1);
        p.setPositionPion(Pion.FOU, Plateau.CHATEAU_RGE);
        Assertions.assertTrue(p.evaluerDeplacementCouronneRge() == 2);
    }

    @Test
    void pionROIestDeplacable() {
        Plateau p = new Plateau(Plateau.DIRECTION_RGE);
        Assertions.assertTrue(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE + 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE - 1));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE + 2));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE - 2));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE + 4));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE - 4));
    }

    @Test
    void pionGAR_VRTestDeplacable() {
        Plateau p = new Plateau(Plateau.DIRECTION_VRT);
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE + 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE - 1));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE + 2));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE - 2));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE + 4));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE - 4));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.BORDURE_VRT - 5));
    }

    @Test
    void pionGAR_RGEestDeplacable() {
        Plateau p = new Plateau(Plateau.DIRECTION_RGE);
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_RGE,Plateau.FONTAINE - 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_RGE,Plateau.FONTAINE + 1));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_RGE,Plateau.FONTAINE - 2));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_RGE,Plateau.FONTAINE + 2));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_RGE,Plateau.FONTAINE - 4));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_RGE,Plateau.FONTAINE + 4));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_RGE,Plateau.CHATEAU_RGE + 1));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_RGE,Plateau.CHATEAU_RGE + 2));
    }

    @Test
    void pionSORetFOUestDeplacable() {
        Plateau p = new Plateau(Plateau.DIRECTION_RGE);
        Assertions.assertTrue(p.pionEstDeplacable(Pion.SOR,Plateau.FONTAINE - 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.SOR,Plateau.FONTAINE + 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.SOR,Plateau.FONTAINE - 4));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.SOR,Plateau.FONTAINE + 4));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.SOR,Plateau.BORDURE_VRT - 4));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.SOR,Plateau.BORDURE_RGE + 4));

        Assertions.assertTrue(p.pionEstDeplacable(Pion.FOU,Plateau.FONTAINE - 2));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.FOU,Plateau.FONTAINE + 2));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.FOU,Plateau.BORDURE_VRT - 4));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.FOU,Plateau.BORDURE_RGE + 4));
    }

    @Test
    void couronneEstDeplacable() {
        Plateau p = new Plateau(Plateau.DIRECTION_RGE);
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
        Plateau p1 = new Plateau(Plateau.DIRECTION_VRT);
        p1.setPositionPion(Pion.ROI, Plateau.CHATEAU_RGE);
        Assertions.assertTrue(p1.estTerminee());

        Plateau p2 = new Plateau(Plateau.DIRECTION_VRT);
        p1.setPositionCouronne(Plateau.CHATEAU_RGE);
        Assertions.assertTrue(p1.estTerminee());
    }

    @Test
    void testEquals() {
        Plateau p1 = new Plateau(Plateau.DIRECTION_VRT);
        Plateau p2 = new Plateau(Plateau.DIRECTION_RGE);
        Plateau p3 = new Plateau(Plateau.DIRECTION_VRT);
        Assertions.assertTrue(p1.equals(p3));
        Assertions.assertFalse(p1.equals(p2));
    }

    @Test
    void testClone() {
        Plateau p1 = new Plateau(Plateau.DIRECTION_VRT);
        Plateau p2 = p1.clone();
        Assertions.assertTrue(p1.equals(p2));
        p2.setPositionPion(Pion.ROI, Plateau.FONTAINE + 1);
        Assertions.assertFalse(p1.equals(p2));
    }
}