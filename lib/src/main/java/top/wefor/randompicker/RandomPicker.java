package top.wefor.randompicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created on 16/8/26.
 * <p/>
 * 适用于音乐随机播放等
 * GitHub: https://github.com/XunMengWinter
 * <p/>
 * latest edited date: 2018-04-09
 *
 * @author ice
 */
public class RandomPicker implements RandomList {

    private List<Integer> mOriginWeightList = new ArrayList<>();
    private List<Integer> mCurrentWeightList = new ArrayList<>();

    private boolean isRepeatable;
    private Integer mNextPickPosition;
    private Random mRandom = new Random();

    private Calculator mCalculator = new IncrementCalculator();


    private RandomPicker() {}


    public RandomPicker(int size) {
        resetList(size);
    }


    /*指定下一次选中的位置*/
    public void setNextPick(int pickedPosition) {
        mNextPickPosition = pickedPosition;
    }

    /*是否允许连续两次出现同一个位置*/
    public void setRepeatable(boolean repeatable) {
        isRepeatable = repeatable;
    }

    public void setCalculator(Calculator calculator){
        mCalculator = calculator;
    }

    @Override
    public void resetList(int size) {
        mOriginWeightList.clear();
        mCurrentWeightList.clear();
        for (int i = 0; i < size; i++) {
            add(1);
        }
    }

    @Override
    public int next() {
        return random();
    }

    @Override
    public void add(int originWeight) {
        mOriginWeightList.add(originWeight);
        mCurrentWeightList.add(calculateWeight(0, originWeight));
    }

    @Override
    public void insert(int index, int originWeight) {
        mOriginWeightList.add(index, originWeight);
        mCurrentWeightList.add(index, calculateWeight(0, originWeight));
    }

    @Override
    public void changeOriginWeight(int index, int originWeight) {
        mOriginWeightList.set(index, originWeight);
    }

    @Override
    public void remove(int index) {
        mOriginWeightList.remove(index);
        mCurrentWeightList.remove(index);
    }

    @Override
    public int getSize() {
        return mOriginWeightList.size();
    }


    /*执行随机算法*/
    private int random() {
        // 若列表长度小于2，则下一次位置必为0.
        if (mCurrentWeightList.size() < 2) {
            return 0;
        }

        int nextPos = 0;
        // 算出下一次选中的位置
        if (mNextPickPosition != null) {
            nextPos = mNextPickPosition;
            mNextPickPosition = null;
        } else {
            long allWeight = 0;
            for (int i = 0; i < mCurrentWeightList.size(); i++) {
                allWeight += mCurrentWeightList.get(i);
            }

            long nextPosInWeight = (long) (mRandom.nextDouble() * allWeight);
            long currentWeight = 0;
            for (int i = 0; i < mCurrentWeightList.size(); i++) {
                currentWeight += mCurrentWeightList.get(i);
                if (currentWeight > nextPosInWeight) {
                    nextPos = i;
                    break;
                }
            }
        }

        // 预先算好下一次的比重
        for (int i = 0; i < mCurrentWeightList.size(); i++) {
            int weight = calculateWeight(mCurrentWeightList.get(i), mOriginWeightList.get(i));
            mCurrentWeightList.set(i, weight);
        }
        if (isRepeatable)
            mCurrentWeightList.set(nextPos, calculateWeight(0, mOriginWeightList.get(nextPos)));
        else
            mCurrentWeightList.set(nextPos, 0);
        return nextPos;
    }

    /*计算下一次的比重*/
    private int calculateWeight(int currentWeight, int originWeight) {
        return mCalculator.calculateWeight(currentWeight, originWeight);
    }

}
