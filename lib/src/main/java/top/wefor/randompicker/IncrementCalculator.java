package top.wefor.randompicker;

/**
 * Created on 2018/4/9.
 *
 * @author ice
 */

public class IncrementCalculator implements Calculator {

    private int mIncrement = 1;

    public IncrementCalculator() {
    }

    public IncrementCalculator(int increment) {
        mIncrement = increment;
    }

    @Override
    public int calculateWeight(int currentWeight, int originWeight) {
        return (currentWeight + mIncrement) * originWeight;
    }
}