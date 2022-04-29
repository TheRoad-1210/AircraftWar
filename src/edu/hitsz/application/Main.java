package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {
    public static final Object LOCK = new Object();


    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //启动初始界面
        Start start = new Start();
        JPanel startJpanel = start.getMainPanel();
        frame.add(startJpanel);
        frame.setVisible(true);

        //等待start关闭
        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //启动game
        Game game = new Game();
        frame.remove(startJpanel);
        frame.add(game);
        frame.setVisible(true);
        game.action();

        //等待game通知
        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //启动输入id界面
        IdInput idInput = new IdInput(game.getPlayer());
        JPanel idInputPanel = idInput.getMainPanel();
        frame.remove(game);
        frame.setContentPane(idInputPanel);
        frame.setVisible(true);

        //等待idinput关闭
        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //通知game继续
        synchronized (Game.GAME_LOCK){
            Game.GAME_LOCK.notify();
        }

        //等待game关闭
        synchronized (LOCK){
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //启动排行榜
        RankList rankList = new RankList();
        JPanel rankListPanel = rankList.getMainPanel();
        frame.remove(game);
        frame.setContentPane(rankListPanel);
        frame.setVisible(true);


    }
}
