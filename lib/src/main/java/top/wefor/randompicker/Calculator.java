package top.wefor.randompicker;

/**
 * 计算器。
 * Created on 2018/4/9.
 *
 * @author ice
 */

public interface Calculator {
    /* calculate the next weight */
    int calculateWeight(int currentWeight, int originWeight);
}
