package edu.hitsz.factory;

/**
 * @author 200111013
 */
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.items.AbstractProp;

public class PropFactory implements Factory{

    public static AbstractProp prop(AbstractAircraft enemyAircraft){
        PropFactory factory = new PropFactory();
        AbstractProp abstractProp = factory.create();
        abstractProp.setLocation(enemyAircraft.getLocationX(),enemyAircraft.getLocationY());
//        System.out.println(abstractProp.getSpeedY());
        return (abstractProp);
    }

    @Override
    public AbstractProp create() {

        double i = Math.random();
        PropFactory propFactory;
        AbstractProp abstractProp;
        double prop1 = 0.3;
        double prop2 = 0.9;
        //随机生成三种道具
        if (i<=prop1){
            propFactory = new HpFactory();
            abstractProp = propFactory.create();
        }
        else if (i<=prop2){
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
