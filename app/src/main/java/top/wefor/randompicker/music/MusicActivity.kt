package top.wefor.randompicker.music

import android.Manifest
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSeekBar
import android.util.Log
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.SeekBar
import top.wefor.randompicker.R
import top.wefor.randompicker.RandomPicker
import top.wefor.randompicker.showTips
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
    var mCountDownTimer: CountDownTimer? = null
    val mHistoryList = arrayListOf<MusicBean>()
    var mHistoryIndex = -1

    lateinit var mCutModeCheckBox: CheckBox
    lateinit var mNextBtnIv: ImageView
    lateinit var mPreviousBtnIv: ImageView
    lateinit var mCoverImgIv: ImageView
    lateinit var mCoverBgIv: ImageView
    lateinit var mProgressSeekBar: AppCompatSeekBar

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

    override fun onDestroy() {
        mCountDownTimer?.cancel()
        super.onDestroy()
    }

    fun initView() {
        setContentView(R.layout.activity_music)
        mCutModeCheckBox = findViewById(R.id.cutMode_checkBox)
        mNextBtnIv = findViewById(R.id.nextBtn_iv)
        mPreviousBtnIv = findViewById(R.id.previousBtn_iv)
        mCoverImgIv = findViewById(R.id.cover_img_iv)
        mCoverBgIv = findViewById(R.id.cover_bg_iv)
        mProgressSeekBar = findViewById(R.id.progress_seekBar)
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
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                showTips(buttonView, (if (isChecked) "Enter" else "Exit") + " Cut mode")

                mCutMode = isChecked
                if (mCutMode)
                    mRandomPicker.enterCutMode()
                else
                    mRandomPicker.exitCutMode()
            }
        })

        mProgressSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mMusicPlayer.seekTo((seekBar?.progress ?: 0) / 100F)
            }
        })
        mNextBtnIv.setOnClickListener { next() }
        mPreviousBtnIv.setOnClickListener { previous() }

        mCountDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                mProgressSeekBar.setProgress((mMusicPlayer.getProgress() * 100).toInt())
            }

            override fun onFinish() {

            }
        }
        mCountDownTimer?.start()
    }

    fun next() {
        if (mMusicProvider.mMusicList.isEmpty()) {
            return
        }

        if (mHistoryIndex >= 0 && mHistoryIndex < mHistoryList.size - 1) {
            mHistoryIndex++
            play(mHistoryList.get(mHistoryIndex)) // play history next
            return
        }

        val startNanoTime = System.nanoTime()
        val index = mRandomPicker.next()
        val costNanoTime = System.nanoTime() - startNanoTime
        Log.i("xyz ", "RandomPicker cost ${costNanoTime / 1_000_000F} millisecond, cutMode: ${mCutMode}, list size: ${mRandomPicker.size}")

        val musicBean = mMusicProvider.mMusicList.get(index)
        play(musicBean)
        mHistoryList.add(musicBean)
        mHistoryIndex = -1 //reset history index
    }

    fun previous() {
        if (mHistoryList.isEmpty()) {
            next()
            return
        }

        if (mHistoryIndex < 0) {
            mHistoryIndex = mHistoryList.size - 1 // reset index
        }

        if (mHistoryIndex > 0) {
            mHistoryIndex-- //play history
        }
        play(mHistoryList.get(mHistoryIndex))
    }

    fun play(musicBean: MusicBean) {
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