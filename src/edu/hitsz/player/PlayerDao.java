package edu.hitsz.player;

/**
 * @author 200111013
 */
public interface PlayerDao {
    /**
     * 向文件中添加玩家的id与分数
     * @param player 玩家实例
     */
    public void doAdd(Player player);

    /**
     * 给目前的player列表排名
     * @return 排名后列表
     */
    public void scoreArray();
}
