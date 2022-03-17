package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.items.AbstractProp;
import edu.hitsz.items.BombSupply;
import edu.hitsz.items.FireSupply;
import edu.hitsz.items.HpSupply;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractAircraft {

    private int shootNum = 1;

    private int power = 20;

    private int direction = -1;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<AbstractBullet> shoot() {
        List<AbstractBullet> enemyres = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() - direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() - direction * 5;
        AbstractBullet abstractBullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            abstractBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            enemyres.add(abstractBullet);
        }
        return enemyres;
    }

    @Override
    public int form() {
        return 2;
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

}
