package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_category.*

class Finished : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished)

        btn_back.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        button2.setOnClickListener{
            startActivity(Intent(this, galleryActivity::class.java))
            finish()
        }
        button3.setOnClickListener{
            startActivity(Intent(this, galleryActivity::class.java))
            finish()
        }
        button4.setOnClickListener{
            startActivity(Intent(this, galleryActivity::class.java))
            finish()
        }
        button5.setOnClickListener{
            startActivity(Intent(this, galleryActivity::class.java))
            finish()
        }
        button6.setOnClickListener{
            startActivity(Intent(this, galleryActivity::class.java))
            finish()
        }
        button7.setOnClickListener{
            startActivity(Intent(this, galleryActivity::class.java))
            finish()
        }
        button8.setOnClickListener{
            startActivity(Intent(this, galleryActivity::class.java))
            finish()
        }
        button9.setOnClickListener{
            startActivity(Intent(this, galleryActivity::class.java))
            finish()
        }
    }
}