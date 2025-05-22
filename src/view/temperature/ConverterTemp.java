package view.temperature;
import model.temperature.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import view.MainFrame;

public class ConverterTemp extends JPanel{
    private final int PANEL_HEIGHT = MainFrame.getFrameHeight();
    private final int PANEL_WIDTH = MainFrame.getFrameWidth();

    //for the buttons
    private JPanel buttonPanel;
    private final int BUTTON_WIDTH_GAP = 4;
    private final int BUTTON_HEIGHT_GAP = 6;
    private final int BUTTON_HEIGHT = 50;
    private final int BUTTON_WIDTH = 60;
    private Border emptyBorder = BorderFactory.createEmptyBorder();     //for design

    //for the input
    private JPanel inputPanel;
    private JLabel inputLabel;
    private final String[] baseSystem = {"Celsius", "Fahrenheit", "Kelvin", "Delisle", "Newton", "Rankine", "Reaumur", "Romer"};
    private JComboBox<String> originalNumberType;
    private JTextArea inputNumber;
    private JTextArea inputReview;
    private JScrollPane inputScroll;
    private JScrollPane reviewScroll;

    public ConverterTemp(){
        this.setBounds(0,20, PANEL_WIDTH, PANEL_HEIGHT - 20);
        this.setBackground(Color.PINK);
        this.setLayout(null);

        displayInput();
        displayButtons();
    }

    void displayInput(){
        inputPanel = new JPanel();
        inputPanel.setBounds(280, 20, 260, 150);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setLayout(null);
        this.add(inputPanel);

        inputLabel = new JLabel("Select the Temperature: ");
        inputLabel.setBounds(0,0,140,20);
        inputLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        inputPanel.add(inputLabel);

        originalNumberType = new JComboBox<String>(baseSystem);
        originalNumberType.setBounds(140, 0, 120, 20);
        originalNumberType.setFocusable(false);
        inputPanel.add(originalNumberType);

        inputNumber = new JTextArea();
        inputNumber.setFont(new Font("Arial", Font.PLAIN, 32));
        inputNumber.setEditable(false);

        inputScroll = new JScrollPane(inputNumber);
        inputScroll.setBounds(5, 25, 250, 50);
        inputScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inputPanel.add(inputScroll);

        inputReview = new JTextArea();
        inputReview.setFont(new Font("Arial", Font.PLAIN, 32));
        inputReview.setEditable(false);

        reviewScroll = new JScrollPane(inputReview);
        reviewScroll.setBounds(5, 100, 250, 50);
        reviewScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inputPanel.add(reviewScroll);
    }

    void displayButtons(){
        buttonPanel = new JPanel();
        buttonPanel.setBounds(280, 180, 260, 342);
        buttonPanel.setLayout(null);
        buttonPanel.setBackground(Color.WHITE);
        this.add(buttonPanel);
        
        String[] buttonLabels = {"CA", "CI", "D", "E", "F", "<-", "9", "A", "B", "C", "5", "6", "7", "8", "1", "2", "3", "4", "0", ".", "-", "="};
        JButton clear = new JButton(buttonLabels[0]);
        clear.setBounds(130, BUTTON_HEIGHT_GAP, BUTTON_WIDTH, BUTTON_HEIGHT);
        clear.setFocusable(false);
        clear.setBorder(emptyBorder);
        clear.setBackground(new Color(0x212122));
        clear.setForeground(Color.WHITE);
        clear.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e){
                    //clear all input and outputs
                    inputNumber.setText(null);
                    inputReview.setText(null);
                }
        });
        buttonPanel.add(clear);

        JButton clearInput = new JButton(buttonLabels[1]);
        clearInput.setBounds(196, BUTTON_HEIGHT_GAP, BUTTON_WIDTH, BUTTON_HEIGHT);
        clearInput.setFocusable(false);
        clearInput.setBorder(emptyBorder);
        clearInput.setBackground(new Color(0x212122));
        clearInput.setForeground(Color.WHITE);
        clearInput.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e){
                    //clear all input and outputs
                    inputNumber.setText(null);
                }
        });
        buttonPanel.add(clearInput);
        
        int buttonX, buttonY, buttonNumber = 2;
        for (int row = 1; row <= 5; row++){
            for (int column = 1; column <= 4; column++){
                buttonX = column*BUTTON_WIDTH_GAP + (column - 1)*BUTTON_WIDTH;
                buttonY = (row + 1)*BUTTON_HEIGHT_GAP + (row)*BUTTON_HEIGHT;

                JButton calcuButton = new JButton(buttonLabels[buttonNumber]);
                calcuButton.setBounds(buttonX, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);
                calcuButton.setFocusable(false);
                calcuButton.setFont(new Font("Verdana", Font.CENTER_BASELINE, 24));
                calcuButton.setBorder(emptyBorder);
                calcuButton.setBackground(new Color(0x212122));
                calcuButton.setForeground(Color.WHITE);
                calcuButton.addActionListener(new ActionListener() {
                    String buttonName = calcuButton.getText();
                    @Override
                    public void actionPerformed(ActionEvent e){
                        switch (buttonName) {
                            case "<-":
                                String text = inputNumber.getText();
                                if (text.length() != 0){
                                    inputNumber.setText(text.substring(0, text.length() - 1));
                                }
                                break;
                            case "=":
                                inputReview.setText(inputNumber.getText());
                                
                                break;
                            default:
                                inputNumber.append(buttonName);
                                break;
                        }
                    }
                });
                buttonPanel.add(calcuButton);
                buttonNumber++;
            }
        }
    }
}
