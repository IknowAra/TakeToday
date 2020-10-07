package kr.hs.emirim.cho.taketoday2

import android.content.Context
import android.graphics.Color
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

class galleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        btn_back.setOnClickListener{
            finish();
        }

        btn_1.setOnClickListener{
            showPopup();
        }
    }
    private fun showPopup(){
        val builder = AlertDialog.Builder(this)
        val viewGroup: ViewGroup = findViewById(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.popup_post,
                viewGroup, false)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setView(dialogView,0,0,0,0)
        alertDialog.show()
        val windowParam = WindowManager.LayoutParams()
        windowParam.copyFrom(alertDialog.window!!.attributes)
        windowParam.width = WindowManager.LayoutParams.WRAP_CONTENT
        windowParam.height = WindowManager.LayoutParams.WRAP_CONTENT
        windowParam.gravity = Gravity.CENTER
        alertDialog.window!!.attributes = windowParam


        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }



}