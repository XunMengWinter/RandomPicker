package top.wefor.randompicker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import top.wefor.randompicker.game.GameActivity

class EntranceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance)
    }

    fun music(view: View){
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun game(view: View){
        startActivity(Intent(this, GameActivity::class.java))
    }
}
