package top.wefor.randompicker.music

import android.Manifest
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import top.wefor.randompicker.R
import top.wefor.randompicker.RandomPicker

/**
 * Created on 2018/7/26.
 * @author ice
 */
class MusicActivity : AppCompatActivity() {

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
        if (requestCode == 11){
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

    fun initData(){
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
        if (enterCutMode)
            mRandomPicker.enterCutMode()
        else
            mRandomPicker.exitCutMode()
    }

    fun next() {
        if (mMusicProvider.mMusicList.isEmpty()){
            return
        }
        val musicBean = mMusicProvider.mMusicList.get(mRandomPicker.next())
        setTitle(musicBean.title)
        mMusicPlayer.next(musicBean.filePath)
    }
}