package kr.hs.emirim.cho.taketoday2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.DrawableContainer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_category.btn_back
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.activity_popup_post.*
import java.util.*

class galleryActivity : AppCompatActivity() {

    var array=Array<String>(20, { ' '.toString() })
    var tag:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)



        array=Cate.sky

        fun <T> List<T>.random() : T {
            val random = Random().nextInt((size))
            return get(random)
        }

        tag=array.random()
        Log.d("", tag)

        todays_tag.setText("오늘의 주제 : "+tag)
        if (intent.hasExtra("name")) {
            cate_title.setText(intent.getStringExtra("name"))
        }


        Cate.hashtag=tag

        btn_back.setOnClickListener{
            finish();
        }

        todays_tag.setOnClickListener {
            startActivity(Intent(this, Upload::class.java))
        }
        
        btn_1.setOnClickListener {
            var dialog = Popup_post()

            dialog.show(supportFragmentManager, "customDialog")
        }
     }
}