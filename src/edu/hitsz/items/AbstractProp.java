package edu.hitsz.items;

import edu.hitsz.basic.AbstractFlyingObject;

public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY){
        super(locationX,locationY,0,0);
    }

    /**
     * 标记道具种类
     * @return
     * 返回道具标号
     */
    abstract public int form();

}
