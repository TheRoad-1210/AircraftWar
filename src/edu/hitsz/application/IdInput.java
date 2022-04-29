package edu.hitsz.application;

import edu.hitsz.player.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author deequoique
 */
public class IdInput {
    private JTextArea id;
    private JTextField input;
    private JPanel under;
    private JPanel top;
    private JPanel root;
    private JButton confirm;

    public IdInput(Player player) {
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setName(input.getText());
                System.out.println(input.getText());
                synchronized (Main.LOCK){
                    Main.LOCK.notify();
                }
            }
        });
    }
    public JPanel getMainPanel() {return root;}
}
