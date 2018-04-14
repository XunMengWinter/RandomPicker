package top.wefor.randompicker;

/**
 * Music Player CutMode
 * 切歌模式。
 * Created on 2018/4/14.
 *
 * @author ice
 */
public interface CutMode {

    /*当前是否处于切歌模式*/
    boolean isCutMode();

    /*进入切歌模式*/
    void enterCutMode();

    /*退出切歌模式*/
    void exitCutMode();
}
