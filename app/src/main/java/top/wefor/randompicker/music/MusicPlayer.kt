package top.wefor.randompicker.music

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.media.MediaPlayer
import java.io.File
import java.io.FileInputStream

/**
 * Created on 2018/7/26.
 * @author ice
 * @GitHub https://github.com/XunMengWinter
 */
class MusicPlayer(lifecycle: Lifecycle) : LifecycleObserver {

    private var mMediaPlayer = MediaPlayer()
    internal var mLifecycle: Lifecycle

    init {
        mLifecycle = lifecycle
        mLifecycle.addObserver(this)
    }

    private fun play(filePath: String) {
        try {
            mMediaPlayer.reset() //重置多媒体
            val file = File(filePath)
            val fd = FileInputStream(file).getFD()
            //指定音频文件地址
            mMediaPlayer.setDataSource(fd, 0, file.length())
            //准备播放
            mMediaPlayer.prepare()

            mMediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setCompletionListener(completionListener: MediaPlayer.OnCompletionListener) {
        mMediaPlayer.setOnCompletionListener(completionListener)
    }

    fun next(musicFilePath: String) {
        play(musicFilePath)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun restart() {
        mMediaPlayer.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stop() {
        mMediaPlayer.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun release() {
        mMediaPlayer.stop()
        mMediaPlayer.release()
        mLifecycle.removeObserver(this)
    }

}