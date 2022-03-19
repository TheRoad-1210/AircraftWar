package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.items.*;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public void raiseHp(int raise){
        hp += raise;
        if(hp >=maxHp){
            hp = maxHp;
        }
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }


    public AbstractProp prop(){
        int x = this.getLocationX();
        int y = this.getLocationY();
        double i = Math.random();
        AbstractProp abstractProp;
        //随机生成三种道具
        if (i<=0.3){
            abstractProp = new HpSupply(x,y);
        }
        else if (i>0.3&&i<=0.6){
            abstractProp = new BombSupply(x,y);
        }
        else{
            abstractProp = new FireSupply(x,y);
        }
        return (abstractProp);
    }


    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<AbstractBullet> shoot();

    /**
     * 飞机类型
     * @return
     * 返回一个标号作为标记
     */
    public abstract int form();

}



