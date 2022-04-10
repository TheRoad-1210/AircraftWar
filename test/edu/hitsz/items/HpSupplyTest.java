package edu.hitsz.items;

import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HpSupplyTest {
    private static HeroAircraft hero;
    private HpSupply hp = new HpSupply();

    @BeforeAll
    static void initALL() {
        hero = HeroAircraft.getInstance();
        hero.decreaseHp(99);
    }

    @Test
    @DisplayName("HS1")
    void use() {
        int a = hero.getHp();
        hp.use(hero);
        int b = hero.getHp();
        assertEquals(20,b-a);
    }
}