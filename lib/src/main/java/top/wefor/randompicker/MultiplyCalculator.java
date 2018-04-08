package top.wefor.randompicker;

/**
 * Created on 2018/4/9.
 *
 * @author ice
 */

public class MultiplyCalculator implements Calculator {

    private int mMultiple = 2;

    public MultiplyCalculator() {
    }

    public MultiplyCalculator(int multiple) {
        mMultiple = multiple;
    }

    @Override
    public int calculateWeight(int currentWeight, int originWeight) {
        return mMultiple * (currentWeight + 1) * originWeight;
    }
}
