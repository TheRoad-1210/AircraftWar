package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * @author 200111013
 */
public class EnemyFactory implements Factory{
    public boolean boss = false;

    @Override
    public AbstractAircraft create() {
        EnemyFactory enemyFactory;
        AbstractAircraft abstractAircraft;
        double prob = 0.7;

        if(boss){
            enemyFactory = new BossFactory();
            System.out.println("boss!!!");
        }
        else if(Math.random()<prob){
            enemyFactory = new MobFactory();
        }
        else {
            enemyFactory = new EliteFactory();
        }
        abstractAircraft = enemyFactory.create();
        return abstractAircraft;
    }
}
