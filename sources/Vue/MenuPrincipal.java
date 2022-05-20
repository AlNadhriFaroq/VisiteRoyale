package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;



public class MenuPrincipal {
    JFrame frame;
    Audio audio;
    MyBouton boutonJouer1vs1, boutonJouerContrIA, boutonChargerPartie;
    MyBouton boutonOptions, boutonTuturiel, boutonCredits, boutonQuitter;
    JLabel label;
    Color colorBouton, colorBoutonSelectionne,colorBoutonClicked, colorBoutonForeGround;
    ImageIcon imgBackGround;
    int larageurFenetre, hauteurFenetre;

    public MenuPrincipal() throws IOException {
        audio = new Audio();
        larageurFenetre = 1080 ;
        hauteurFenetre = 720 ;


        colorBouton = new Color(92, 19, 169);
        colorBoutonSelectionne = new Color(251, 133, 30);
        colorBoutonClicked = new Color(30, 132, 234);
        colorBoutonForeGround = new Color(255, 255, 255);






    }

    private MyBouton createBouton(String nom, ActionListener action) {
        MyBouton bouton = new MyBouton(nom);
        bouton.setBounds(0,0,70,30);

        bouton.addActionListener(action);
        bouton.setFocusable(false);
        return bouton;
    }

    public void run() throws IOException {
        frame = new JFrame("Menu");
        audio.boucler(0);
        frame.setSize(larageurFenetre, hauteurFenetre);
        imgBackGround=  new ImageIcon(new ImageIcon(String.valueOf(new File("resources/Images/bg2.jpg"))).getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_DEFAULT));
        label = new JLabel(imgBackGround);
        label.setLayout(new BorderLayout());
        //label.setSize(larageurFenetre,hauteurFenetre);


        JPanel panelButtons =new JPanel(new GridLayout());
        panelButtons.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelButtons.setBackground(Color.GRAY);
        panelButtons.setLayout(new BoxLayout(panelButtons,BoxLayout.PAGE_AXIS));
        panelButtons.setBackground(new Color(0,0,0,0));
        panelButtons.setPreferredSize(new Dimension(400,400));

        boutonJouer1vs1 = createBouton("Jouer 1vs1",ActionEvent->{
            System.out.println("joueur1vs1");
        });

        boutonJouerContrIA = createBouton("Jouer contre IA", ActionEvent->{
            String[] niveauxIA ={ "Debutant", "Inter", "Pro", "Expert"};
            int retour = JOptionPane.showOptionDialog(frame, "Choisir niveau IA: ", "Difficulté IA",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,null,niveauxIA,"");
            if( retour!=JOptionPane.CLOSED_OPTION){
                switch (retour){
                    case 0 :
                        System.out.println("Vous avez choisi IA debutant");
                        break;
                    case 1:
                        System.out.println("Vous avez choisi IA inter");
                        break;
                    case 2:
                        System.out.println("Vous avez choisi IA pro");
                        break;
                    case 3:
                        System.out.println("Vous avez choisi IA expert");
                        break;
                }
            }
        });

        boutonChargerPartie = createBouton("Charger une Partie", ActionEvent->{
            System.out.println("Charger une Partie");
        });

        boutonOptions = createBouton("Options", ActionEvent->{
            System.out.println("Options");
        });

        boutonTuturiel = createBouton("Tuturiel", ActionEvent->{
            System.out.println("Tuturiel");
        });

        boutonCredits = createBouton("Credits", ActionEvent->{
            System.out.println("credits");
        });

        boutonQuitter= createBouton("Quitter", ActionEvent->{
            if(JOptionPane.showConfirmDialog(frame,"Voulez vous quitter Visite Royale?","Quitter",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                System.exit(0);
        });

        panelButtons.add(boutonJouer1vs1);
        panelButtons.add(Box.createGlue());
        panelButtons.add(boutonJouerContrIA);
        panelButtons.add(Box.createGlue());
        panelButtons.add(boutonChargerPartie);
        panelButtons.add(Box.createGlue());
        panelButtons.add(boutonOptions);
        panelButtons.add(Box.createGlue());
        panelButtons.add(boutonTuturiel);
        panelButtons.add(Box.createGlue());
        panelButtons.add(boutonCredits);
        panelButtons.add(Box.createGlue());
        panelButtons.add(boutonQuitter);

        label.add(panelButtons,BorderLayout.PAGE_END);
        

        frame.setContentPane(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void main(String[] args) throws IOException {
            MenuPrincipal menu = new MenuPrincipal();
            menu.run();

        }

}
