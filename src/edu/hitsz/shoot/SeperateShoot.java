package edu.hitsz.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 86181
 * 散射策略
 */
public class SeperateShoot implements ShootStrategy{
    @Override
    public List<AbstractBullet> way(AbstractAircraft aircraft) {
        List<AbstractBullet> res = new LinkedList<>();
        int x =aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection()*2;
        int speedY;
        int shootNum = 5;
        AbstractBullet abstractBullet;
        if(aircraft instanceof HeroAircraft) {
            speedY = aircraft.getSpeedY() + aircraft.getDirection()*5;
            for(int i=0; i<shootNum; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                abstractBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, i-2, speedY, aircraft.getPower());
                res.add(abstractBullet);
            }
        }
        else {
            speedY = aircraft.getSpeedY() - aircraft.getDirection()*5;
            for(int i=0; i<shootNum; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                abstractBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, i-2, speedY, aircraft.getPower());
                res.add(abstractBullet);
            }
        }
        return res;
    }
}
