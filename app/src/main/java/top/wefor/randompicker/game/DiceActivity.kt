package top.wefor.randompicker.game

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import top.wefor.randompicker.Calculator
import top.wefor.randompicker.IncrementCalculator
import top.wefor.randompicker.R
import top.wefor.randompicker.RandomPicker

/**
 * 用 RandomPicker 实现各种随机机制
 */
class DiceActivity : AppCompatActivity() {

    private lateinit var mRadioGroup: RadioGroup
    private lateinit var mWeightLayout: ViewGroup
    private lateinit var mSpinner: Spinner
    private lateinit var mPercentTv: TextView
    private lateinit var mBoardTv: TextView
    private val mRandomPicker = RandomPicker()

    private val mCriticalPos = 0
    private var mTotal = 0
    private var mCritical = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView(savedInstanceState)
    }

    fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_dice)

        mRadioGroup = findViewById(R.id.candidate_layout)
        mWeightLayout = findViewById(R.id.weight_layout)
        mSpinner = findViewById(R.id.spinner)
        mPercentTv = findViewById(R.id.percent_tv)
        mBoardTv = findViewById(R.id.board_tv)

        val spinnerList = arrayListOf<String>()
        spinnerList.add("originWeight++ random") //默认随机
        spinnerList.add("independent random") //独立随机。即"真随机"
        spinnerList.add("Blizzard War3 critical random") //war3暴击随机
        spinnerList.add("draw random: one by one") //翻牌随机：一次一张地翻

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList)
        mSpinner.adapter = adapter
        mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                run {
                    mRandomPicker.exitCutMode() //退出切歌模式
                    when (position) {
                        0 -> setRandomPicker(IncrementCalculator())
                        1 -> setRandomPicker(Calculator { position, currentWeight, originWeight -> 1 }) //实现独立随机比较简单，weight永远返回同一个常数即可。
                        2 -> setRandomPicker(Calculator { position, currentWeight, originWeight ->
                            // war3随机有个特点：有几率触发100%暴击，但是不会出现0%暴击的情况。
                            // 计算公式大致为，综合暴击率20%时：设定初始暴击几率5%，未触发暴击则暴击率翻倍，若触发暴击则重置为初始暴击几率。
                            var weight = 0
                            if (position == mCriticalPos) {
                                if (currentWeight == 0)//说明需要初始化暴击几率
                                    weight = 5
                                else
                                    weight = Math.min(currentWeight * 2, 100)
                            } else {
                                try {
                                    weight = (100 - mRandomPicker.getWeight(mCriticalPos)) / 4
                                } catch (e: Exception) {
                                    weight = (100 - 5) / 4
                                }
                            }
                            //这种情况，走RandomPicker有点多此一举(而且需要把特定数放在第一位)，而且效率不高。不过好处在于接口统一。
                            weight
                        })
                        3 -> {
                            //随机翻牌，其实开启切歌模式即可。
                            mRandomPicker.enterCutMode()
                            setRandomPicker(Calculator { position, currentWeight, originWeight -> 1 })
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        mSpinner.setSelection(0)

        mBoardTv.setText("用 RandomPikcer 实现几种常见的随机机制"
                + "\n\n1.RandomPicker默认模式，被选中的权重变为0，未被选中的权重++。"
                + "\n\n2.独立随机，即游戏领域所说的'真随机'，每次随机都不受之前结果影响。"
                + "\n\n3.暴雪war3暴击机制，综合暴击率20%时：设定初始暴击几率5%，未触发暴击则暴击率翻倍，若触发暴击则重置为初始暴击几率。"
                + "\n\n4.翻牌随机，一次翻一张牌。每一轮翻牌中，翻过的牌不会再出现。与音乐播放里的切歌模式一致。"
        )
    }

    fun setRandomPicker(calculator: Calculator) {
        Log.i("xyz", "setRandomPicker " + calculator.calculateNextWeight(0, 0, 1))
        mRandomPicker.setCalculator(calculator)
        mRandomPicker.setRepeatable(true)
        mRandomPicker.resetList(mRadioGroup.childCount, 1)

        mTotal = 0
        mCritical = 0
        showCriticalTv()
    }

    fun showCriticalTv() {
        val percent = mCritical * 100 / Math.max(mTotal, 1)
        mPercentTv.setText("暴击率: $percent%      $mCritical/$mTotal")
    }

    fun random(view: View) {
        view.isEnabled = false
        val pos = mRandomPicker.next()

        mRadioGroup.check(mRadioGroup.getChildAt(pos).id)
        for (index in 0..mWeightLayout.childCount - 1) {
            (mWeightLayout.getChildAt(index) as TextView).text =
                    mRandomPicker.getWeight(index).toString()
        }

        mTotal++
        if (pos == mCriticalPos) {
            mCritical++
        }
        showCriticalTv()
        view.isEnabled = true
    }

    fun machine(view: View) {
        startActivity(Intent(this, GameActivity::class.java))
    }
}
