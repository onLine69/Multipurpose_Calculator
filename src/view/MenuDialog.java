package view;
import view.basicCalculator.BasicCalculator;
import view.programmerCalculator.ProgCalculator;
import view.temperature.ConverterTemp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuDialog extends JDialog{
        //for the menu dialog
        private final int MENU_WIDTH = 400;
        private final int MENU_HEIGHT = 450;
        private final Dimension MENU_DIMENSION = new Dimension(MENU_WIDTH, MENU_HEIGHT);
        private final int BUTTON_WIDTH = 100;
        private final int BUTTON_HEIGHT = 100;
        private final int BUTTON_GAP = 25;
        private JButton conversion;

        public MenuDialog(){
            displayMenu();
        }
        
        void displayMenu(){
            this.setTitle("App Menu");
            this.getContentPane().setPreferredSize(MENU_DIMENSION);
            this.getContentPane().setBackground(Color.BLACK);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setLayout(null);
            this.pack();
            this.setLocationRelativeTo(null);     //must be set after pack()
            displayMenuButtonIcons();
            this.setResizable(false);
            this.setVisible(true);
        }

        private void displayMenuButtonIcons(){
            String[] iconNames = {"areaConversion.png", "currencyConversion.png", "lengthConversion.png", "powerConversion.png", "pressureConversion.png", "speedConversion.png", "temperatureConversion.png", "volumeConversion.png", "weightConversion.png"};
            String[] buttonNames = {"Basic", "Scientific", "Programmer", "Area", "Length", "Speed", "Temperature", "Volume", "Weight"};
            int buttonCount = 0;
            
            for (int row = 0; row < 3; row++){
                for (int column = 0; column < 3; column++){
                    ImageIcon icon = new ImageIcon("./sources/Menu Icons/" + iconNames[buttonCount]);
                    icon = resizeIcons(icon);

                    conversion = new JButton(buttonNames[buttonCount],icon);
                    conversion.setBounds(BUTTON_GAP + (BUTTON_GAP + BUTTON_HEIGHT)*column, BUTTON_GAP + (BUTTON_GAP + BUTTON_WIDTH)*row, BUTTON_WIDTH, BUTTON_HEIGHT);
                    conversion.setFocusable(false);
                    conversion.setHorizontalTextPosition(JButton.CENTER);
                    conversion.setVerticalTextPosition(JButton.BOTTOM);
                    if (buttonCount != 0 && buttonCount != 2 && buttonCount != 6){
                        conversion.setEnabled(false);
                    }
                    conversion.addActionListener(new ActionListener() {
                        String buttonName = conversion.getText();
                        @Override
                        public void actionPerformed(ActionEvent e){
                            //TODO: Finish every case
                            switch(buttonName){
                                case "Basic":
                                    System.out.println("Basic");
                                    JPanel basicCalculator = new BasicCalculator();
                                    basicCalculator.setName("Basic Calculator");
                                    new MainFrame(basicCalculator);
                                    break;
                                case "Scientific":
                                    System.out.println("Scientific");
                                    break;
                                case "Programmer":
                                    System.out.println("Programmer");
                                    JPanel progCalculator = new ProgCalculator();
                                    progCalculator.setName("Programmer Calculator");
                                    new MainFrame(progCalculator);
                                    break;
                                case "Area":
                                    System.out.println("Area");
                                    break;
                                case "Length":
                                    System.out.println("Length");
                                    break;
                                case "Speed":
                                    System.out.println("Speed");
                                    break;
                                case "Temperature":
                                    JPanel tempCalculator = new ConverterTemp();
                                    tempCalculator.setName("Temperature Converter");
                                    new MainFrame(tempCalculator);
                                    System.out.println("Temperature");
                                    break;
                                case "Volume":
                                    System.out.println("Volume");
                                    break;
                                case "Weight":
                                    System.out.println("Weight");
                                    break;
                            }
                            dispose();
                        }   
                    });
                    this.add(conversion);
                    buttonCount++;
                }
            }
        }

        private ImageIcon resizeIcons(ImageIcon icon){
            Image resHamburger = icon.getImage();
            resHamburger = resHamburger.getScaledInstance(50, 50,  java.awt.Image.SCALE_FAST); // scale it the smooth way
            icon = new ImageIcon(resHamburger);

            return icon;
        }
    }
