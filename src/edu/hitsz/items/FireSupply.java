package edu.hitsz.items;

public class FireSupply extends AbstractProp{
    public FireSupply(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public int form() {
        return 1;
    }
}
