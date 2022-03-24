package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteFactory extends EnemyFactory{
    @Override
    public AbstractAircraft create() {
        return new EliteEnemy(
                (int)(Math.random()*(Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                (int) (Math.random()*8),
                8,
                50);
    }
}
