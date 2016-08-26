package top.wefor.randompicker;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created on 16/8/26.
 * <p/>
 * 适用于音乐随机播放等
 * GitHub: https://github.com/XunMengWinter
 * <p/>
 * latest edited date: 2016-08-26
 *
 * @author ice
 */
public class RandomPicker {

    private ArrayList<Integer> mOriginWeightList = new ArrayList<>();
    private ArrayList<Integer> mCurrentWeightList = new ArrayList<>();
    private ArrayList<Integer> mHistoryList = new ArrayList<>();

    private int mMultiplyNumber = 1;
    private int mAddNumber = 1;
    private int mPickedPosition;
    private boolean isRepeatable;
    private Integer mNextPickPosition;
    Random mRandom = new Random();

    public RandomPicker() {
        //默认一个，避免报错。
        new RandomPicker(1);
    }

    public RandomPicker(int size) {
        initSize(size);
    }

    /*设置累乘积数*/
    public void setMultiplyNumber(int multiplyNumber) {
        mMultiplyNumber = multiplyNumber;
    }

    /*设置累加积数*/
    public void setAddNumber(int addNumber) {
        mAddNumber = addNumber;
    }

    /*指定下一次选中的位置*/
    public void setNextPick(int pickedPosition) {
        mNextPickPosition = pickedPosition;
    }

    /*是否允许连续两次出现同一个位置*/
    public void setRepeatable(boolean repeatable) {
        isRepeatable = repeatable;
    }

    /*初始化列表长度*/
    public void initSize(int size) {
        mOriginWeightList.clear();
        mCurrentWeightList.clear();
        mHistoryList.clear();
        for (int i = 0; i < size; i++)
            add();
    }

    /*获得当前条目数*/
    public int getSize() {
        return mOriginWeightList.size();
    }

    /*获取历史条目的位置列表*/
    public ArrayList<Integer> getHistoryList() {
        return mHistoryList;
    }


             /*上为配置参数*/
             /*下为逻辑实现*/


    /*获得下一个随机条目的位置*/
    public int next() {
        random();
        mHistoryList.add(mPickedPosition);
        return mPickedPosition;
    }

    public void add() {
        // 默认每个条目的比重为1.
        add(getSize(), 1);
    }

    /*添加一个条目*/
    public void add(int index, int weight) {
        mOriginWeightList.add(index, weight);
        mCurrentWeightList.add(index, calculateWeight(0, weight));
    }

    /*修改一个条目的比重*/
    public void changeOriginWeight(int index, int weight) {
        mOriginWeightList.set(index, weight);
        int currentWeight = mCurrentWeightList.get(index);
        mCurrentWeightList.set(index, currentWeight / mOriginWeightList.get(index) * weight);
    }

    /*移除一个条目*/
    public void remove(int index) {
        mOriginWeightList.remove(index);
        mCurrentWeightList.remove(index);
    }

    /*执行随机算法*/
    private void random() {
        // 算出下一次选中的位置
        if (mNextPickPosition != null) {
            mPickedPosition = mNextPickPosition;
            mNextPickPosition = null;
        } else {
            long allCount = 0;
            for (int i = 0; i < mCurrentWeightList.size(); i++) {
                allCount += mCurrentWeightList.get(i);
            }

            long randomLong = (long) (mRandom.nextDouble() * allCount);
            long currentLong = 0;
            for (int i = 0; i < mCurrentWeightList.size(); i++) {
                currentLong += mCurrentWeightList.get(i);
                if (currentLong > randomLong) {
                    mPickedPosition = i;
                    break;
                }
            }
        }

        // 若列表长度小于2，则下一次位置必为0.
        if (mCurrentWeightList.size() < 2) {
            mPickedPosition = 0;
            return;
        }

        // 预先算好下一次的比重
        for (int i = 0; i < mCurrentWeightList.size(); i++) {
            int weight = calculateWeight(mCurrentWeightList.get(i), mOriginWeightList.get(i));
            mCurrentWeightList.set(i, weight);
        }
        if (isRepeatable)
            mCurrentWeightList.set(mPickedPosition, calculateWeight(0, mOriginWeightList.get(mPickedPosition)));
        else
            mCurrentWeightList.set(mPickedPosition, 0);
    }

    /*计算下一次的比重*/
    private int calculateWeight(int currentWeight, int originWeight) {
        return (currentWeight + mAddNumber) * mMultiplyNumber * originWeight;
    }

}
