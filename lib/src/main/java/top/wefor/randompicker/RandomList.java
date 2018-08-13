package top.wefor.randompicker;

/**
 * Created on 2018/4/9.
 *
 * @author ice
 */

public interface RandomList {
    /*重置列表*/
    void resetList(int size, int originWeight);

    /*获得下一个随机条目的位置*/
    int next();

    /*添加一个条目至末尾*/
    void add(int originWeight);

    /*插入一个条目*/
    void insert(int index, int originWeight);

    /*修改一个条目的比重*/
    void changeOriginWeight(int index, int originWeight);

    /*移除一个条目*/
    void remove(int index);

    /*获取长度*/
    int getSize();

    /*获取某个条目的权重*/
    int getWeight(int index);
}
