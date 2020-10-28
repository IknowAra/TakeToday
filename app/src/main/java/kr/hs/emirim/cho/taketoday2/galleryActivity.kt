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

        btn_back.setOnClickListener{
            finish();
        }

        todays.setOnClickListener {
            val intent= Intent(this, Upload::class.java)
            startActivity(intent)
        }
        
        btn_1.setOnClickListener{
            var dialog=Popup_post()

            dialog.show(supportFragmentManager, "customDialog")

//            onResume();
        }


    }

//    private fun showPopup(){
//        val builder = AlertDialog.Builder(this)
//        val viewGroup: ViewGroup = findViewById(android.R.id.content)
//        val dialogView: View =
//            LayoutInflater.from(this).inflate(R.layout.popup_post,
//                viewGroup, false)
//        val alertDialog: AlertDialog = builder.create()
//        alertDialog.setView(dialogView,0,0,0,0)
//        alertDialog.show()
//
//        val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val display = windowManager.defaultDisplay
//        val size = Point()
//        display.getSize(size)
//
//        val windowParam = WindowManager.LayoutParams()
//        windowParam.copyFrom(alertDialog.window!!.attributes)
//        windowParam.width = WindowManager.LayoutParams.MATCH_PARENT
//        windowParam.height = WindowManager.LayoutParams.MATCH_PARENT
//        windowParam.gravity = Gravity.CENTER
//        alertDialog.window!!.attributes = windowParam
//
//
//        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        val viewGroup: ViewGroup = findViewById(android.R.id.content)
//        val dialogView: View =
//            LayoutInflater.from(this).inflate(R.layout.popup_post,
//                viewGroup, false)
//
//        val builder = AlertDialog.Builder(this)
//        val alertDialog: AlertDialog = builder.create()
//
//        alertDialog.setView(dialogView)
//
//        val size = Point()
//        val params: ViewGroup.LayoutParams? = alertDialog?.window?.attributes
//        val deviceWidth = size.x
//        params?.width = (deviceWidth * 0.9).toInt()
//        alertDialog?.window?.attributes = params as WindowManager.LayoutParams
//        alertDialog.show()
//    }

}