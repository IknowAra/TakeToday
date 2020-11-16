package kr.hs.emirim.cho.taketoday2

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class LodingDialogActivity constructor(context: Context): Dialog(context) {
    private var c: Context? = null

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        c = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loding_dialog)
        val logo = findViewById<ImageView>(R.id.loadingIcon)
        val animation: Animation = AnimationUtils.loadAnimation(c, R.anim.loading)
        logo.animation = animation
    }


}