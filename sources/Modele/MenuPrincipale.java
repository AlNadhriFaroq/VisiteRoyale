package Modele;

public class MenuPrincipale {
    public static final int ETAT_ACCUEIL=  0 ;
    public static final int ETAT_MENU_PRINCIPALE =  1 ;
    public static final int ETAT_EN_JEU =  2 ;
    public static final int ETAT_MENU_JEU =  3 ;
    public static final int ETAT_MENU_PARAMETRES =  4 ;
    public static final int ETAT_TUTORIEL =  5 ;
    public static final int ETAT_CREDITS =  6 ;
    public static final int ETAT_FIN_APP =  7 ;

    Jeu jeu;
    int etat;

    public MenuPrincipale(){
        jeu = new Jeu() ;
        etat = ETAT_MENU_PRINCIPALE;
    }

    public void jouer(){
        jeu.nouvellePartie();
        etat = ETAT_EN_JEU ;
    }


    public void chargerPartie(){

    }

    public void parametres(){
        etat = ETAT_MENU_PARAMETRES;

    }

    public void quitter(){
        etat = ETAT_FIN_APP ;
    }
}
