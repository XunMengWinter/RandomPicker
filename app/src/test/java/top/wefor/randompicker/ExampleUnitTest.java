package top.wefor.randompicker;

import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);

        final int size = 30;
        RandomPicker randomPicker = new RandomPicker();
        randomPicker.setCalculator(new Calculator() {
            @Override
            public int calculateNextWeight(int currentWeight, int originWeight) {
                if (currentWeight == 0) {
                    //加大连击概率
                    return size;
                }
                return currentWeight + originWeight + 1;
            }
        });
        randomPicker.resetList(size, 1);
        randomPicker.setRepeatable(true);
        final int luckyNum = 7;
        int award = 0;
        int magic = 0;//发生连击的次数
        int lastNum = -1;
        int currentMax = 0;
        int max = 0;//最大连击数
        for (int i = 0; i < 10_000; i++) {
            int next = randomPicker.next();
            if (next == luckyNum) {
                award++;
                if (next == lastNum) {
                    magic++;
                    currentMax++;
                } else {
                    currentMax = 1;
                }
                max = Math.max(max, currentMax);
            } else {
                max = Math.max(max, currentMax);
                currentMax = 0;
            }
            lastNum = next;
        }
        System.out.println("award: " + award + "  magic: " + magic + "  max: " + max + " \n" + String.format("1/%.2f", 10_000f / award));
    }

    private double rateM(int m, int n) {
        double mm = Math.sqrt(m);
        double nn = Math.sqrt(n);
        return 1f * mm / (mm + nn);
    }
}