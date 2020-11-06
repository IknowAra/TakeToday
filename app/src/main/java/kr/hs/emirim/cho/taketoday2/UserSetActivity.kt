package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_set.*
import kr.hs.emirim.cho.taketoday2.Login
import kr.hs.emirim.cho.taketoday2.MainActivity
import kr.hs.emirim.cho.taketoday2.R
import kr.hs.emirim.cho.taketoday2.User
import java.util.*

class UserSetActivity : AppCompatActivity() {

    private lateinit var user_name:String;
    private lateinit var user_tele:String;
    //private lateinit var user_email:String;
    private var user_id: String? = null
    private var user_email: String? = null

    private lateinit var mAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_set)

        mAuth = FirebaseAuth.getInstance()
        val user= mAuth.currentUser

        setup_btn.setOnClickListener {
            user_name=userName.text.toString()
            user_tele=userTele.text.toString()
            user?.let{
                user_email = user.email
                user_id=user.uid
            }

            if (!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(user_tele)) {

                adduser()
            }
        }
    }

    private fun adduser() {

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val user = User(user_name, user_tele)
        val userMap: MutableMap<String, String> =
            HashMap()
        userMap["email"] = user_email.toString()
        userMap["name"] = user.getName()
        userMap["tele"] = user.getTele()
        Toast.makeText(this, user_id.toString(), Toast.LENGTH_SHORT).show()
        db.collection("Users").document(user_id.toString()).set(userMap)
            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "유저 정보가 등록됨", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this, Login::class.java))
                } else {
                    val error = task.exception!!.message
                    Toast.makeText(
                        this,
                        "Firestore Error : $error",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("에러 : ", error!!)
                }
            })
    }
}