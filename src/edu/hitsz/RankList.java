package edu.hitsz;

import edu.hitsz.player.Player;
import edu.hitsz.player.PlayerDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;



/**
 * @author deequoique
 */
public class RankList {
    private JTable score;
    private JPanel root;
    private JPanel top;
    private JScrollPane scoreroot;
    private JPanel under;
    private JButton delete;
    Object LOCK;


    public RankList() throws IOException, ClassNotFoundException {
        PlayerDaoImpl playerDao = new PlayerDaoImpl();
        playerDao.read();
        playerDao.scoreArray();
        ArrayList<Player> players = playerDao.getPlayers();
        String[][] tableData = new String[players.size()][4];
        String[] columnName = {"排名","id","得分","时间"};
        int i;
        for(i = 0;i < players.size();i++) {
            int j = i+1;
            tableData[i][0] = String.valueOf(j);
            tableData[i][1] = players.get(i).getName();
            tableData[i][2] = String.valueOf(players.get(i).getScore());
            tableData[i][3] = players.get(i).getTime();
        }
        //表格模型
        DefaultTableModel model = new DefaultTableModel(tableData, columnName){
            @Override public boolean isCellEditable(int row, int col){
                return false; }
        };
        //从表格模型那里获取数据
        score.setModel(model);
        scoreroot.setViewportView(score);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = score.getSelectedRow();
                if(row != -1){

                    String time = (String) model.getValueAt(row,3);
                    model.removeRow(row);
                    players.removeIf(player -> Objects.equals(player.getTime(), time));
                    try {
                        playerDao.storage();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        }
    });

}
    public JPanel getMainPanel() {return root;}

    public static void main(String[] args) {
        JFrame frame = new JFrame("RankList");
        try {
            frame.setContentPane(new RankList().root);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
