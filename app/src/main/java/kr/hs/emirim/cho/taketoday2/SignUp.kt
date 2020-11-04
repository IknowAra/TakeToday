package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private lateinit var userEmail: String;
    private lateinit var userPass: String;
    private var current_user: FirebaseUser? = null
    private lateinit var confirmPass: String;
    private var user_name: String? = null
    private var user_tele: String? = null
    private var user_id: String? = null
    private var user_email: String? = null


    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        current_user = mAuth.currentUser
        user_id = current_user!!.uid
        Log.d("addUser 지금 user: ", current_user.toString())

        btn_verify.setOnClickListener {
            userEmail = email.text.toString()
            userPass = password.text.toString()
            confirmPass = confirm_password.text.toString()
            if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPass) && !TextUtils.isEmpty(confirmPass)) {
                if (userPass == confirmPass) {
                    reg_progress.visibility = View.VISIBLE
                    mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                        .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                            if (task.isSuccessful) {
                                current_user!!.sendEmailVerification()
                                    .addOnCompleteListener {
                                        if(task.isSuccessful){
                                            Toast.makeText(
                                                this,
                                                "회원가입 완료 이메일 인증을 확인해주세요",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            layout_setup.visibility = View.VISIBLE
                                            btn_verify.visibility=View.VISIBLE
                                            btn_sign.setOnClickListener{
                                                addUser()
                                            }
                                        }
                                    }
                            } else {
                                val errorMessage = task.exception!!.message
                                Toast.makeText(
                                    this,
                                    "Error : $errorMessage",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            reg_progress.visibility = View.INVISIBLE
                        })
                } else {
                    Toast.makeText(this, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT)
                        .show()
                }

            }


        }
    }
    private fun addUser() {
        Toast.makeText(this, "t시작", Toast.LENGTH_SHORT).show()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val user = User(user_name, user_tele, false)
        current_user = mAuth.currentUser
        user_name=userName.text.toString()
        user_tele=userTele.text.toString()
        user_email = current_user!!.email
        user_id = current_user!!.uid

        val userMap = hashMapOf(
            "email" to user_email.toString(),
            "name" to user.getName(),
            "tele" to user.getTele(),
            "current" to listOf<String>(),
            "finish" to listOf<String>()
        )

        Log.d("addUser 지금 user: ", current_user.toString())
        db.collection("Users").document(user_id.toString()).set(userMap)
            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                Toast.makeText(this, "들어옴", Toast.LENGTH_SHORT)
                    .show()
                if (task.isSuccessful) {
                    Log.d("current_user!!.isEmailVerified: ",
                        current_user!!.isEmailVerified.toString() //왜 false냐
                    )
                    if(current_user!!.isEmailVerified){

                        Toast.makeText(this, "유저 정보가 등록됨", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, Login::class.java))
                        finish()
                    }else {
                        Toast.makeText(
                            this,
                            "회원가입 완료 이메일 인증을 확인해주세요",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

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