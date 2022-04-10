package edu.hitsz.aircraft;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EliteEnemyTest {
    private static HeroAircraft hero;
    private static MobEnemy enemy;

    @BeforeAll
    static void initALL() {

        hero = HeroAircraft.getInstance();
        enemy = new MobEnemy(1,1,1,1,1);
    }

    @ParameterizedTest
    @DisplayName("EE1")
    @CsvSource({"1,1,2,3","1,2,2,3","1,200,1,220"})
    void crash(int x1,int x2, int y1, int y2) {
        hero.setLocation(x1,y1);
        enemy.setLocation(x2,y2);
        assertEquals(true,enemy.crash(hero));

    }


    @Test
    @DisplayName("EE2")
    void vanish() {
        enemy.vanish();
        assertEquals(true,enemy.notValid());
    }
}