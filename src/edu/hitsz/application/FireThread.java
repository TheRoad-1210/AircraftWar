package edu.hitsz.application;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shoot.MobShoot;
import edu.hitsz.shoot.SeperateShoot;

/**
 * @author deequoique
 */
public class FireThread extends Thread{
    private HeroAircraft heroAircraft;

    public FireThread(HeroAircraft heroAircraft){
        this.heroAircraft = heroAircraft;
    }
    @Override
    public void run() {
        heroAircraft.setStrategy(new SeperateShoot());
        try {
            FireThread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        heroAircraft.setStrategy(new MobShoot());
    }
}
