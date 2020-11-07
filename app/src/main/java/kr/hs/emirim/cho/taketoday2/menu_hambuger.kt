package kr.hs.emirim.cho.taketoday2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu_hambuger.*

class menu_hambuger : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_hambuger)

        drawer.setBackgroundResource(R.drawable.back)
    }
}