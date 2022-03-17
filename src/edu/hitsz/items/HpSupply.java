package edu.hitsz.items;

public class HpSupply extends AbstractProp{
    public HpSupply(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public int form() {
        return 2;
    }
}
