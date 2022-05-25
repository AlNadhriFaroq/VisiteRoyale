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
    JButton boutonJouer1vs1, boutonJouerContrIA, boutonChargerPartie;
    JButton boutonOptions, boutonTuturiel, boutonCredits, boutonQuitter;
    JLabel label;
    Color colorBouton, colorBoutonSelectionne,colorBoutonClicked, colorBoutonForeGround;
    ImageIcon imgBackGround;
    int larageurFenetre, hauteurFenetre;

    public MenuPrincipal() throws IOException {
        larageurFenetre = 960 ;
        hauteurFenetre = 680 ;
        imgBackGround=  new ImageIcon(new ImageIcon(String.valueOf(new File("resources/Images/bg.jpg"))).getImage().getScaledInstance(larageurFenetre, hauteurFenetre, Image.SCALE_DEFAULT));
        label = new JLabel(imgBackGround);

        colorBouton = new Color(19, 22, 169);
        colorBoutonSelectionne = new Color(251, 133, 30);
        colorBoutonClicked = new Color(30, 132, 234);
        colorBoutonForeGround = new Color(255, 255, 255);






    }

    private JButton createBouton(String nom, ActionListener action) {
        JButton  bouton = new JButton(nom);
        //bouton.setBackground(new Color(227, 2, 2, 0));
       // bouton.setIcon(new ImageIcon(img));
        bouton.setPreferredSize(new Dimension(120,50));

        bouton.setMargin(new Insets(10,40,10,40));
        bouton.setForeground(colorBoutonForeGround);
        bouton.setBackground(colorBouton);
        bouton.setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));
        bouton.addActionListener(action);
        bouton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                //super.mouseEntered(e);
               bouton.setBackground(colorBoutonSelectionne);
            }

            @Override
            public void mouseExited(MouseEvent e) {
               bouton.setBackground(colorBouton);
            }

        });
        bouton.setFocusable(false);

        return bouton;
    }

    public void run() throws IOException {
        frame = new JFrame("Menu");
        frame.setSize(larageurFenetre, hauteurFenetre);
        label.setLayout(new FlowLayout());
        label.setLayout(new BorderLayout());
        label.setSize(larageurFenetre,hauteurFenetre);


        JPanel panelButtons =new JPanel(new GridLayout());
        panelButtons.setLayout(new BoxLayout(panelButtons,BoxLayout.PAGE_AXIS));
        panelButtons.setBackground(new Color(0,0,0,0));

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
        panelButtons.add(boutonJouerContrIA);
        panelButtons.add(boutonChargerPartie);
        panelButtons.add(boutonOptions);
        panelButtons.add(boutonTuturiel);
        panelButtons.add(boutonCredits);
        panelButtons.add(boutonQuitter);

        panelButtons.setPreferredSize(new Dimension(200,200));
        panelButtons.setLocation(30,300);
        label.add(panelButtons,BorderLayout.PAGE_END);

        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void main(String[] args) throws IOException {
            MenuPrincipal menu = new MenuPrincipal();
            menu.run();

        }

}
