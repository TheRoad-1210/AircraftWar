package edu.hitsz.items;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

public abstract class AbstractProp extends AbstractFlyingObject {


    public AbstractProp(){}

    /**
     * 实现用途
     */
    abstract public void use(HeroAircraft heroAircraft);

}
