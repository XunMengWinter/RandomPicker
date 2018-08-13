package top.wefor.randompicker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
//        RandomPicker randomPicker = new RandomPicker();
//        randomPicker.resetList(20, 1);
//        for (int i = 0;i < 20; i++){
//            int times = 0;
//            while (true){
//                times++;
//                if (randomPicker.next() == 7)
//                    break;
//            }
//            System.out.println(times);
//        }

//        randomTest();
//        for (int i=1;i<20;i++){
//            System.out.print(i);
        pokerTest(9);
//        }
    }

    /**
     * 重置翻牌随机。
     * 综合概率为 2/(n+1)。
     */
    private void pokerTest(int num) {
        List<Integer> list = new ArrayList<>();
        for (int x = 0; x < num; x++)
            list.add(x);
        Collections.shuffle(list);

        Random random = new Random();
        int luckyCount = 0;
        int lastNum = -1;
        int comboCount = 0;
        for (int i = 0; i < 10_000; i++) {
            int index = random.nextInt(list.size());
            int lucky = list.remove(index);
            if (lucky == num / 2) { //取中间值作为中奖数
                if (lastNum == lucky) {
                    //连中
                    comboCount++;
                }
                luckyCount++;
                //抽中后重新洗牌
                list.clear();
                for (int x = 0; x < num; x++)
                    list.add(x);
                Collections.shuffle(list);
            }
            lastNum = lucky;
        }
        System.out.println("10,000 times, comboCount: " + comboCount);
        System.out.println("pokers = " + num + "  , probability = " + (luckyCount / 10_000f));
    }

    private void randomTest() {
        final int size = 30;
        final RandomPicker randomPicker = new RandomPicker();
        randomPicker.setCalculator(new Calculator() {
            @Override
            public int calculateNextWeight(int position, int currentWeight, int originWeight) {
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
                randomPicker.resetCurrentWeight(1);
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