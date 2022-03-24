package edu.hitsz.factory;

import edu.hitsz.items.AbstractProp;
import edu.hitsz.items.FireSupply;

public class FireFactory extends PropFactory{
    @Override
    public AbstractProp create() {
        return new FireSupply();
    }
}
