package edu.hitsz.items;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * @author 200111013
 */
public class FireSupply extends AbstractProp{

    public FireSupply() {
    }

    @Override
    public void use(HeroAircraft heroAircraft) {
        System.out.println("FireSupply active!");
    }


}
