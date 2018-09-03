package top.wefor.randompicker.game

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import top.wefor.randompicker.R
import top.wefor.randompicker.RandomPicker

class ScoreShuffleActivity : AppCompatActivity() {

    private lateinit var mGridLayout: GridLayout
    private lateinit var mPercentTv: TextView
    private lateinit var mSilenceView: View

    private val mRandomPicker = RandomPicker()

    private val mCriticalPos = 0
    private var mTotal = 0
    private var mCritical = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_shuffle)

        mGridLayout = findViewById(R.id.gridLayout)
        mPercentTv = findViewById(R.id.percent_tv)
        mSilenceView = findViewById(R.id.silence_view)

        resetRandomPicker()

        mGridLayout.post {
            for (index in 0..mGridLayout.childCount - 1) {
                val imageView: ImageView = mGridLayout.getChildAt(index) as ImageView
                imageView.setOnClickListener {
                    imageView.isEnabled = false
                    val randomIndex = mRandomPicker.next()
                    if (randomIndex == mCriticalPos) {
                        //命中，重新洗牌
                        mSilenceView.visibility = View.VISIBLE
                        imageView.setImageResource(R.drawable.ic_critical)
                        mCritical++
                        mGridLayout.postDelayed({
                            resetRandomPicker()
                            resetGridLayout()
                            mSilenceView.visibility = View.GONE
                        }, 1_000)
                    } else {
                        imageView.setImageResource(R.drawable.ic_attack)
                    }
                    mTotal++
                    showCriticalTv()
                }
            }
        }
    }

    fun showCriticalTv() {
        val percent = mCritical * 100 / Math.max(mTotal, 1)
        mPercentTv.setText("暴击率: $percent%      $mCritical/$mTotal")
    }

    fun resetRandomPicker() {
        mRandomPicker.exitCutMode()
        mRandomPicker.resetList(9, 1)
        mRandomPicker.enterCutMode()
    }

    fun resetGridLayout() {
        for (index in 0..mGridLayout.childCount - 1) {
            val imageView: ImageView = mGridLayout.getChildAt(index) as ImageView
            imageView.setImageResource(R.drawable.ic_unknown)
            imageView.isEnabled = true
        }
    }

}
