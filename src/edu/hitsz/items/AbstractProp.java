package edu.hitsz.items;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY){
        super(locationX,locationY,0,0);
    }

    public static AbstractProp prop(AbstractAircraft enemyAircraft){
        int x = enemyAircraft.getLocationX();
        int y = enemyAircraft.getLocationY();
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
     * 实现用途
     * @return null
     */
    abstract public void use(HeroAircraft heroAircraft);

}
