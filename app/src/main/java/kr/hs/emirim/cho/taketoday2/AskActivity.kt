package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_ask.*
import kotlinx.android.synthetic.main.activity_ask.btn_back
import java.util.HashMap

class AskActivity : AppCompatActivity() {

    private lateinit var ask_contents:String
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private var user_id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask)

        mAuth = FirebaseAuth.getInstance()
        user= mAuth.currentUser!!

        btn_ask.setOnClickListener{
            ask_contents=et_ask.text.toString()
            if (user != null) {
                user_id=user.uid
            }
            addAsk()
        }

        btn_back.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun addAsk() {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val askMap: MutableMap<String, String> =
            HashMap()
        askMap["user_email"] = user.email.toString()
        askMap["contents"] = ask_contents
        db.collection("Ask").document(user_id.toString()).set(askMap)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "문의가 등록되었습니다.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, AskActivity::class.java))
                    finish()
                }else{
                    val error = task.exception!!.message
                    Toast.makeText(
                        this,
                        "Firestore Error : $error",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("에러 : ", error!!)
                }
            }
    }
}