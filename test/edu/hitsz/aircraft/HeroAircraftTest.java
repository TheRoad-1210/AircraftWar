package edu.hitsz.aircraft;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;



class HeroAircraftTest {
    private static HeroAircraft hero;


    @BeforeAll
    static void initALL() {
        hero = HeroAircraft.getInstance();
    }

    @ParameterizedTest
    @DisplayName("HA1")
    @ValueSource(ints = {2,4,10,20})
    void decreaseHp(int decrease) {

        int hp1 = hero.hp;
        hero.decreaseHp(decrease);
        int hp2 = hero.hp;
        assertEquals(decrease,hp1-hp2);

    }

    @ParameterizedTest
    @DisplayName("HA2")
    @ValueSource(ints = {2,4,10,50})
    void raiseHp(int raise) {
        hero.decreaseHp(99);
        int hp1 = hero.hp;
        hero.raiseHp(raise);
        int hp2 = hero.hp;
        assertEquals(hp2-hp1,raise);

    }


}