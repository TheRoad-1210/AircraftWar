package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.shoot.MobShoot;
import edu.hitsz.shoot.ShootContext;

import java.util.List;

/**
 * 精英敌机，可发射子弹
 * @author 200111013
 */

public class EliteEnemy extends AbstractAircraft {

    private int shootNum = 1;

    private int power = 20;

    private int direction = -1;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<AbstractBullet> shoot() {
        ShootContext context = new ShootContext(new MobShoot());
        return context.executrStrategy(this);
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
