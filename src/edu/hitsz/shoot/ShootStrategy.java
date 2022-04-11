package edu.hitsz.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;

import java.util.List;

public interface ShootStrategy {

    abstract public List<AbstractBullet> way (AbstractAircraft aircraft);
}
