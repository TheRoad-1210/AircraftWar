package edu.hitsz.application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import static edu.hitsz.application.Main.LOCK;

/**
 * @author deequoique
 */
public class Start {
    private JPanel root;
    private JButton easy;
    private JButton normal;
    private JButton hard;
    private JPanel top;
    private JPanel middle;
    private JPanel bottom;
    private JCheckBox voice;

    public Start() {
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                synchronized (Main.LOCK){
                    LOCK.notify();
                }
            }

        });
        normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg4.jpg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                synchronized (LOCK){
                    LOCK.notify();
                }
            }

        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                synchronized (LOCK){
                    LOCK.notify();
                }
            }

        });
        voice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public JPanel getMainPanel() {return root;}



    public static void main(String[] args) {
        JFrame frame = new JFrame("Start");
        frame.setContentPane(new Start().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
