package view.basicCalculator;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import model.basicCalculator.*;
import view.MainFrame;

public class BasicCalculator extends JPanel{
    private final int PANEL_HEIGHT = MainFrame.getFrameHeight();
    private final int PANEL_WIDTH = MainFrame.getFrameWidth();
    //for basic calculator
    private JTextArea displayInputExpression;
    private JTextArea resulTextField;
    private JTextArea historyDisplay;
    private JPanel historyPanel;
    private JPanel inputPanel;
    private JPanel buttonsPanel;
    private JScrollPane historyScroll;
    private final int BUTTON_HEIGHT = 58;
    private final int BUTTON_WIDTH = 80;

    private Border emptyBorder = BorderFactory.createEmptyBorder();     //for design

    public BasicCalculator(){
        this.setBounds(0,20, PANEL_WIDTH, PANEL_HEIGHT - 20);
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        displayHistory();
        displayCalcuInputPanel();
        displayCalcuButtonsPanel();
    }

    /*
     * Display where the input and results.
     */
    private void displayCalcuInputPanel(){
        inputPanel = new JPanel();
        inputPanel.setBounds(0,0, PANEL_WIDTH - 220, 150);
        inputPanel.setLayout(null);
        this.add(inputPanel);

        displayInputExpression = new JTextArea();
        displayInputExpression.setBounds(0,0, inputPanel.getWidth(), 50);
        displayInputExpression.setEditable(false);
        displayInputExpression.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        displayInputExpression.setBorder(emptyBorder);
        displayInputExpression.setBackground(Color.BLACK);
        displayInputExpression.setForeground(Color.WHITE);
        inputPanel.add(displayInputExpression);

        resulTextField = new JTextArea();
        resulTextField.setBounds(0,50, inputPanel.getWidth(), 100);
        resulTextField.setEditable(false);
        resulTextField.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        resulTextField.setBorder(emptyBorder);
        resulTextField.setBackground(Color.BLACK);
        resulTextField.setForeground(Color.WHITE);
        inputPanel.add(resulTextField);
    }

    /*
     * Display the buttons for the calculator.
     */
    private void displayCalcuButtonsPanel(){
        buttonsPanel = new JPanel();
        buttonsPanel.setBounds(5, 165, inputPanel.getWidth() - 10, 350);
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.setLayout(null);
        this.add(buttonsPanel);

        //"\u221A" is a square root symbol
        String[] buttonLabels = {"\u221A","%","C","<-","^","(",")","/","7","8","9","x","4","5","6","-","1","2","3","+","00","0",".","="};
        int buttonNumber = 0;
        int buttonX;
        int buttonY;
        for (int row = 0; row < 6; row++){
            for (int column = 0; column < 4; column++){
                buttonX = column*BUTTON_WIDTH;
                buttonY = row*BUTTON_HEIGHT;

                JButton calcuButton = new JButton(buttonLabels[buttonNumber]);
                calcuButton.setBounds(buttonX, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);
                calcuButton.setFocusable(false);
                calcuButton.setFont(new Font("Verdana", Font.CENTER_BASELINE, 24));
                calcuButton.setBorder(emptyBorder);
                calcuButton.setBackground(new Color(0x212122));
                calcuButton.setForeground(Color.WHITE);
                calcuButton.addActionListener(new ActionListener(){
                    String buttonName = calcuButton.getText();
                    @Override
                    public void actionPerformed(ActionEvent e){
                        String result;

                        switch(buttonName){
                            case "\u221A":  //square root sign
                                resulTextField.setText(null);
                                result = CalcuGroups.simplifyExpression(displayInputExpression.getText());
                                displayInputExpression.setText(result);
                                historyDisplay.append("\u221A" + displayInputExpression.getText() + " = " + CalcuEvaluate.squareRoot(result) + "\n");
                                displayInputExpression.setText(CalcuEvaluate.squareRoot(result));
                                resulTextField.append(displayInputExpression.getText());
                                break;
                            case "%":
                                resulTextField.setText(null);
                                result = CalcuGroups.simplifyExpression(displayInputExpression.getText() + "/100");
                                historyDisplay.append(displayInputExpression.getText() + "% = " + result + "\n");
                                displayInputExpression.setText(result);
                                resulTextField.append(result);
                                break;
                            case "=":
                                resulTextField.setText(null);
                                result = CalcuGroups.simplifyExpression(displayInputExpression.getText());
                                resulTextField.append(result);
                                historyDisplay.append(displayInputExpression.getText() + " = " + result + "\n");
                                break;
                            case "C":
                                resulTextField.setText(null);
                                displayInputExpression.setText(null);
                                break;
                            case "<-":
                                String text = displayInputExpression.getText();
                                if (text.length() != 0){
                                    displayInputExpression.setText(text.substring(0, text.length() - 1));
                                }
                                break;
                            default:
                                if (resulTextField.getText().length() > 0){
                                    displayInputExpression.setText(resulTextField.getText());
                                    displayInputExpression.append(buttonName);
                                    resulTextField.setText(null);
                                } else {
                                    displayInputExpression.append(buttonName);
                                }
                                break;    
                        }
                    }
                });
                buttonsPanel.add(calcuButton);
                buttonNumber++;
            }
        }
    }

    /*
     * Displays the history of the calculator.
     */
    private void displayHistory(){
        historyPanel = new JPanel();
        historyPanel.setBounds(330, 0, 210, PANEL_HEIGHT - 20);
        historyPanel.setBackground(Color.RED);
        historyPanel.setLayout(null);
        this.add(historyPanel);

        historyDisplay = new JTextArea();
        historyDisplay.setEditable(false);
        historyDisplay.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        historyDisplay.setBorder(emptyBorder);
        historyDisplay.setBackground(Color.BLACK);
        historyDisplay.setForeground(Color.WHITE);

        historyScroll = new JScrollPane(historyDisplay);
        historyScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        historyScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        historyScroll.setBounds(0,0,210, PANEL_HEIGHT - 20);
        historyPanel.add(historyScroll);
    }
}
