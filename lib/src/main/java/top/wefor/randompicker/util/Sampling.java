package top.wefor.randompicker.util;

import java.util.Random;

/**
 * Created on 2018/10/5.
 *
 * @author ice
 */
public class Sampling {

    /**
     * 随机取样
     *
     * @param bound 总数
     * @param count 需要抽取的个数
     * @return 返回一个有序数组
     */
    private static int[] getRandomSamples(int bound, int count) {
        if (bound < 1 || count < 1 || bound <= count)
            return null;

        boolean[] fillArray = new boolean[bound];
        for (int i = 0; i < bound; i++) {
            fillArray[i] = false; //用false标示未填充,true表示已填充。
        }

        Random random = new Random();
        int fillCount = 0;
        final int randomNumCount = Math.min(count, bound - count); //随机填充的数目不超过一半
        while (fillCount < randomNumCount) {
            int num = random.nextInt(bound);
            if (!fillArray[num]) {
                fillArray[num] = true;
                fillCount++;
            }
        }

        int[] samples = new int[count];
        //如果随机抽取的数量与所需相等，则取该集合；否则取补集。
        if (randomNumCount == count) {
            int index = 0;
            for (int i = 0; i < bound; i++) {
                if (fillArray[i])
                    samples[index++] = i;
            }
        } else {
            //取补集
            int index = 0;
            for (int i = 0; i < bound; i++) {
                if (!fillArray[i])
                    samples[index++] = i;
            }
        }
        return samples;
    }


}
