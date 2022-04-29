package edu.hitsz.items;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.FireThread;

/**
 * @author 200111013
 */
public class FireSupply extends AbstractProp{

    public FireSupply() {
    }

    @Override
    public void use(HeroAircraft heroAircraft) {
        FireThread fireThread = new FireThread(heroAircraft);
        fireThread.start();
    }


}
