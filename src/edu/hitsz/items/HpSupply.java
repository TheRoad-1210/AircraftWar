package edu.hitsz.items;

import edu.hitsz.aircraft.HeroAircraft;

public class HpSupply extends AbstractProp{
    public HpSupply(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void use(HeroAircraft heroAircraft) {
        heroAircraft.raiseHp(20);
    }


}
