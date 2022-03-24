package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;

public class EnemyFactory implements Factory{
    @Override
    public AbstractAircraft create() {
        EnemyFactory enemyFactory;
        AbstractAircraft abstractAircraft;
        if(Math.random()<0.8){
            enemyFactory = new MobFactory();
        }
        else {
            enemyFactory = new EliteFactory();
        }
        abstractAircraft = enemyFactory.create();
        return abstractAircraft;
    }
}
