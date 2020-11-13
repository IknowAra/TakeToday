package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_policy.*

class PolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)

        btn_go.setOnClickListener{
            if (check_agree.isChecked()) {
                startActivity(Intent(this, SignUp::class.java))
                finish()
            } else {
                Toast.makeText(this, "개인정보 수집에 동의해주세요.", Toast.LENGTH_LONG)
            }
        }
    }
}