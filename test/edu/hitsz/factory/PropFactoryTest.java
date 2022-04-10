package edu.hitsz.factory;

import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.items.AbstractProp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PropFactoryTest {
    private static MobEnemy enemy;
    @BeforeAll
    static void initALL() {

        enemy = new MobEnemy(1,1,1,1,1);
    }

    @ParameterizedTest
    @DisplayName("PF1")
    @CsvSource({"1,1","1,2","200,220"})
    void prop(int x, int y) {

        AbstractProp prop = PropFactory.prop(enemy);
        enemy.setLocation(x,y);
        System.out.println(prop.getLocationX()+prop.getLocationY());

    }
}