package edu.hitsz.items;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shoot.MobShoot;
import edu.hitsz.shoot.SeperateShoot;

/**
 * @author 200111013
 */
public class FireSupply extends AbstractProp{

    public FireSupply() {
    }

    @Override
    public void use(HeroAircraft heroAircraft) {
        Runnable fireThread = ()->{
            heroAircraft.setStrategy(new SeperateShoot());
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            heroAircraft.setStrategy(new MobShoot());
        };
        new Thread(fireThread).start();
    }


}
