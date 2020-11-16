package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val mRunnable:Runnable= Runnable {
        if(!isFinishing){
            //로고를 위로 움직인다.
            slideUp(splash_title, 1500)

            Handler().postDelayed({
                var fadeInAnimation=AnimationUtils.loadAnimation(this, R.anim.fade_in)
                google_logo.startAnimation(fadeInAnimation)
                google_logo.visibility=View.VISIBLE

//                startActivity(Intent(this, Login::class.java))
//                finish()
            }, 2000)

            Handler().postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 3000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splash_layout.setBackgroundResource(R.drawable.back2)
        google_logo.visibility=View.INVISIBLE

        Handler().postDelayed(mRunnable,1000)

    }

    fun slideUp(view: View, time:Int){
        val animation=TranslateAnimation(0f,0f,0f,-(view.height*3).toFloat())
        animation.duration=time.toLong()
        animation.fillAfter=true
        view.startAnimation(animation)
    }

}