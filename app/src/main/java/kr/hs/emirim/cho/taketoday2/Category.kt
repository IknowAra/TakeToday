package kr.hs.emirim.cho.taketoday2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_category.*

class Category : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        btn_back.setOnClickListener{
            finish();
        }
    }
}