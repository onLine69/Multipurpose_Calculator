package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class MainFrame extends JFrame{
    //main app dimensions
    private static final int FRAME_HEIGHT = 550;
    private static final int FRAME_WIDTH = 550;
    private final Dimension MAIN_DIMENSION = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
    private Border emptyBorder = BorderFactory.createEmptyBorder();     //for design
    
    public MainFrame(JPanel currentDisplay){
        this.setTitle(currentDisplay.getName());
        this.getContentPane().setPreferredSize(MAIN_DIMENSION);
        this.getContentPane().setBackground(Color.BLACK);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.pack();
        this.setLocationRelativeTo(null); // must be set after pack()
        this.setResizable(false);
        this.setVisible(true);
        
        appIcon();
        showMenuButton();
        showCredits();
        
        this.add(currentDisplay);
    }

    public MainFrame(){
        new MenuDialog();
    }

    public static int getFrameHeight(){
        return FRAME_HEIGHT;
    }
    public static int getFrameWidth(){
        return FRAME_WIDTH;
    }

    void showMenuButton(){
        JButton menu = new JButton("Menu");
        menu.setBounds(5, 0, 100, 20);
        menu.setBackground(Color.BLACK);
        menu.setForeground(Color.WHITE);
        menu.setFocusable(false);
        menu.setBorder(emptyBorder);
        menu.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e){
                    dispose();
                    new MenuDialog();
                }
        });
        this.add(menu);
    }

    void showCredits(){
        JLabel credits = new JLabel("Made by: Caine Bautista (2022-0378)");
        credits.setBounds(350, 0, 200, 20);
        credits.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        credits.setForeground(Color.WHITE);
        this.add(credits);
    }

    void appIcon(){
        ImageIcon calcuIcon = new ImageIcon("./sources/calcuIcon.png");
        Image resCalcuIcon = calcuIcon.getImage();

        this.setIconImage(resCalcuIcon);
    }
}
