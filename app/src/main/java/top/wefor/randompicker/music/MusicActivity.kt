package top.wefor.randompicker.music

import android.Manifest
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import top.wefor.randompicker.R
import top.wefor.randompicker.RandomPicker
import java.io.File

/**
 * Created on 2018/7/26.
 * @author ice
 * @GitHub https://github.com/XunMengWinter
 */
class MusicActivity : AppCompatActivity() {

    var mCutMode = false
    val mMusicPlayer: MusicPlayer = MusicPlayer(getLifecycle())
    lateinit var mMusicProvider: MusicProvider
    lateinit var mRandomPicker: RandomPicker

    lateinit var mCutModeCheckBox: CheckBox
    lateinit var mNextBtnIv: ImageView
    lateinit var mCoverImgIv: ImageView
    lateinit var mCoverBgIv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 11)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 11) {
            initData()
        }
    }

    fun initView() {
        setContentView(R.layout.activity_music)
        mCutModeCheckBox = findViewById(R.id.cutMode_checkBox)
        mNextBtnIv = findViewById(R.id.nextBtn_iv)
        mCoverImgIv = findViewById(R.id.cover_img_iv)
        mCoverBgIv = findViewById(R.id.cover_bg_iv)
    }

    fun initData() {
        mMusicProvider = MusicProvider(this)
        mRandomPicker = RandomPicker(mMusicProvider.mMusicList.size, 1)

        mMusicPlayer.setCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mediaPlayer: MediaPlayer?) {
                next()
            }
        })

        mCutModeCheckBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                setCutMode(p1)
            }
        })

        mNextBtnIv.setOnClickListener { next() }
    }

    fun setCutMode(enterCutMode: Boolean) {
        mCutMode = enterCutMode
        if (enterCutMode)
            mRandomPicker.enterCutMode()
        else
            mRandomPicker.exitCutMode()
    }

    fun next() {
        if (mMusicProvider.mMusicList.isEmpty()) {
            return
        }
        val startNanoTime = System.nanoTime()
        val index= mRandomPicker.next()
        val costNanoTime = System.nanoTime() - startNanoTime
        Log.i("xyz ", "RandomPicker cost ${costNanoTime/1_000_000F} millisecond, cutMode: ${mCutMode}, list size: ${mRandomPicker.size}")

        val musicBean = mMusicProvider.mMusicList.get(index)
        setTitle(musicBean.title)
        showImage(musicBean.imageFilePath)
        mMusicPlayer.next(musicBean.filePath)
    }

    private fun showImage(filePath: String?) {
        if (!filePath.isNullOrEmpty()) {
            val file = File(filePath)
            if (file.exists()) {
                mCoverImgIv.setImageURI(Uri.fromFile(file));
                return
            }
        }
        mCoverImgIv.setImageResource(R.drawable.ic_music_note_white_36dp)
    }
}