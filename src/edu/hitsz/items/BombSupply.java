package edu.hitsz.items;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * @author 200111013
 */
public class BombSupply extends AbstractProp{

    public BombSupply() {
    }

    @Override
    public void use(HeroAircraft heroAircraft) {
        System.out.println("BombSupply active!");
    }


}
