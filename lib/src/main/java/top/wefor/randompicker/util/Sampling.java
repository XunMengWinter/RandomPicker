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
    public static int[] getRandomSamples(int bound, int count) {
        if (bound < 1 || count < 1 || bound <= count)
            return null;

        int[] array = new int[bound];
        for (int i = 0; i < bound; i++) {
            array[i] = 0; //用0标示未填充
        }

        Random random = new Random();
        int fillCount = 0;
        final int randomNumCount = Math.min(count, bound - count); //随机填充的数目不超过一半
        while (fillCount < randomNumCount) {
            int num = random.nextInt(bound);
            if (array[num] == 0) {
                array[num] = 1;
                fillCount++;
            }
        }

        int[] samples = new int[count];
        //如果随机抽取的数量与所需相等，则取该集合；否则取补集。
        if (randomNumCount == count) {
            int index = 0;
            for (int i = 0; i < bound; i++) {
                if (array[i] != 0)
                    samples[index++] = i;
            }
        } else {
            //取补集
            int index = 0;
            for (int i = 0; i < bound; i++) {
                if (array[i] == 0)
                    samples[index++] = i;
            }
        }
        return samples;
    }


}
