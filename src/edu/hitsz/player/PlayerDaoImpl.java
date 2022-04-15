package edu.hitsz.player;

import java.io.*;
import java.util.ArrayList;

/**
 * @author deequoique
 */
public class PlayerDaoImpl implements PlayerDao{
    public ArrayList<Player> getPlayers() {
        return players;
    }

    private ArrayList<Player> players;

    public PlayerDaoImpl() {
    }

    @Override
    public void doAdd(Player player) {
        players.add(player);
    }

    @Override
    public void scoreArray() {
        CompareScore cs = new CompareScore();
        players.sort(cs);

    }

    public void storage() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("score"));
        oos.writeObject(players);
        oos.flush();
        oos.close();
    }

    public void read() throws IOException, ClassNotFoundException {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("score"));
            ArrayList<Player> ply =(ArrayList<Player>) ois.readObject();
            for(Player player :ply) {
                players.add(player);
            }
            ois.close();
    }
}
