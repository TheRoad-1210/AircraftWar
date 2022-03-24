package edu.hitsz.items;

import edu.hitsz.aircraft.HeroAircraft;

public class FireSupply extends AbstractProp{

    public FireSupply() {

    }

    @Override
    public void use(HeroAircraft heroAircraft) {
        System.out.println("FireSupply active!");
    }


}
