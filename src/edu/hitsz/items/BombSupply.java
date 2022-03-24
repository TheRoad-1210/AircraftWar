package edu.hitsz.items;

import edu.hitsz.aircraft.HeroAircraft;

public class BombSupply extends AbstractProp{

    public BombSupply() {

    }

    @Override
    public void use(HeroAircraft heroAircraft) {
        System.out.println("BombSupply active!");
    }


}
