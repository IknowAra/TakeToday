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

class galleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

       // val cate=Cate()

//        for(i in cate.arg.indices){
//            Log.d("Cate : #*(#$&@$*$&(@*&&@$(@)@(#*)!(#)*#*)@#*#@", cate.arg[i])
//        }

        btn_back.setOnClickListener{
            finish();
        }

        todays.setOnClickListener {
            val intent= Intent(this, Upload::class.java)
            startActivity(intent)
        }
        
        btn_1.setOnClickListener {
            var dialog = Popup_post()

            dialog.show(supportFragmentManager, "customDialog")
        }
     }
}