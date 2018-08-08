package top.wefor.randompicker.game

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import top.wefor.randompicker.R

/**
 * TODO Button'set' 可以设置左侧的博弈参数。
 * TODO 随机算法至少提供4种：RandomPicker、区间伪随机、独立事件随机、激进随机。了解更多：说明包含的各种算法。
 * TODO 庄家收益面板 统计'set'之后的博弈信息。
 * TODO 玩家列表 默认填充两个，item需包含这些参数：game按钮、game局数、命中局数、当前营收或亏损、最大营收。
 * TODO 底部按钮 添加与移除玩家；自动游戏开关：开启后，每一秒钟每个玩家随机参与或不参与游戏。
 * 玩家之间相互独立，每个玩家都对应一个随机生成器。
 *
 * game博弈
 *
 * Created on 2018/8/8.
 * @author ice
 * @GitHub https://github.com/XunMengWinter
 */
class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

    }
}
