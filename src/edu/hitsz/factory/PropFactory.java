package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.items.AbstractProp;

public class PropFactory implements Factory{

    public static AbstractProp prop(AbstractAircraft enemyAircraft){
        PropFactory factory = new PropFactory();
        AbstractProp abstractProp = factory.create();
        abstractProp.locationX = enemyAircraft.getLocationX();
        abstractProp.locationY = enemyAircraft.getLocationY();
        return (abstractProp);
    }

    @Override
    public AbstractProp create() {

        double i = Math.random();
        PropFactory propFactory;
        AbstractProp abstractProp;
        //随机生成三种道具
        if (i<=0.3){
            propFactory = new HpFactory();
            abstractProp = propFactory.create();
        }
        else if (i<=0.6){
            propFactory = new BombFactory();
            abstractProp = propFactory.create();
        }
        else{
            propFactory = new FireFactory();
            abstractProp = propFactory.create();
        }
        return abstractProp;

    }
}
