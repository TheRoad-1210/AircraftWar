package edu.hitsz.items;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shoot.SeperateShoot;

/**
 * @author 200111013
 */
public class FireSupply extends AbstractProp{

    public FireSupply() {
    }

    @Override
    public void use(HeroAircraft heroAircraft) {
        heroAircraft.setStrategy(new SeperateShoot());
    }


}
