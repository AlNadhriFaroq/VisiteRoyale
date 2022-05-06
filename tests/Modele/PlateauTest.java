package Modele;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlateauTest {

    @Test
    void testGetPion() {
        Plateau p = new Plateau(Plateau.DIRECTION_VRT);
        Assertions.assertTrue(p.getPion(Pion.ROI).getPosition() == Plateau.FONTAINE);
    }

    @Test
    void testGetCouronne() {
        Plateau p = new Plateau(Plateau.DIRECTION_VRT);
        Assertions.assertTrue(p.getCouronne().getPosition() == Plateau.FONTAINE);

    }

    @Test
    void testGetDeplacementCouronneVrt() {
        Plateau p = new Plateau(Plateau.DIRECTION_VRT);
        Assertions.assertTrue(p.getDeplacementCouronneVrt() == 0);
        p.getPion(Pion.GAR_VRT).setPosition(Plateau.CHATEAU_VRT);
        Assertions.assertTrue(p.getDeplacementCouronneVrt() == 1);
        p.getPion(Pion.FOU).setPosition(Plateau.CHATEAU_VRT);
        Assertions.assertTrue(p.getDeplacementCouronneVrt() == 2);
    }

    @Test
    void testGetDeplacementCouronneRge() {
        Plateau p = new Plateau(Plateau.DIRECTION_RGE);
        Assertions.assertTrue(p.getDeplacementCouronneRge() == 0);
        p.getPion(Pion.GAR_RGE).setPosition(Plateau.CHATEAU_RGE);
        Assertions.assertTrue(p.getDeplacementCouronneRge() == 1);
        p.getPion(Pion.FOU).setPosition(Plateau.CHATEAU_RGE);
        Assertions.assertTrue(p.getDeplacementCouronneRge() == 2);
    }

    @Test
    void pionRoiEstDeplacable() {
        Plateau p = new Plateau(Plateau.DIRECTION_RGE);
        Assertions.assertTrue(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE + 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE -1));

        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE + 2));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE - 2));

        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE + 4));
        Assertions.assertFalse(p.pionEstDeplacable(Pion.ROI,Plateau.FONTAINE - 4));
    }

    @Test
    void pionGRDVertEstDeplacable() {
        Plateau p = new Plateau(Plateau.DIRECTION_VRT);
        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE + 1));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE -1));

        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE + 2));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE - 2));

        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE + 4));
        Assertions.assertTrue(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.FONTAINE - 4));

        Assertions.assertFalse(p.pionEstDeplacable(Pion.GAR_VRT,Plateau.BORDURE_VRT - 5));
    }

    @Test
    void pionGRDRougetEstDeplacable() {
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
        p1.getPion(Pion.ROI).setPosition(Plateau.CHATEAU_RGE);
        Assertions.assertTrue(p1.estTerminee());

        Plateau p2 = new Plateau(Plateau.DIRECTION_VRT);
        p1.getCouronne().setPosition(Plateau.CHATEAU_RGE);
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
        p2 = p1.clone();
        p2.getPion(Pion.ROI).setPosition(Plateau.FONTAINE + 1);
        Assertions.assertFalse(p1.equals(p2));

    }

}