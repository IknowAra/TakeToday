package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    var db: FirebaseFirestore? = null
    private var mAuth: FirebaseAuth? = null
    private var current_User: FirebaseUser? = null
    private var user_email: String?=null
    private var user_id: String? = null
    private var loginEmail: String? = null
    private var loginPass: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        current_User= mAuth!!.currentUser
        user_id = current_User!!.uid

        login_btn.setOnClickListener(View.OnClickListener {

            user_email=current_User?.email
            Log.d("#)(*(#$)*#(*$)(============>>>>>>", user_email.toString())
            loginEmail = login_email.text.toString()
            loginPass = login_password.text.toString()
            if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)) {
                login_progress.setVisibility(View.VISIBLE)
                mAuth!!.signInWithEmailAndPassword(loginEmail!!, loginPass!!)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if(current_User!!.isEmailVerified || current_User!=null) {
                                Toast.makeText(
                                    this,
                                    "로그인 성공 :" + user_email,
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this, MainActivity::class.java))
                            } else{
                                Toast.makeText(
                                    this,
                                    "메일로 보낸 링크를 확인해주세요." ,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {
                            val errorMessage = task.exception!!.message
                            Toast.makeText(
                                this,
                                "error : $errorMessage",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        login_progress.setVisibility(View.INVISIBLE)
                    }
            }
        })

        login_reg.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this,
                    SignUp::class.java
                )
            )
        })
    }

//    override fun onStart() {
//        super.onStart()
//        //Toast.makeText(this, "hihihihi", Toast.LENGTH_SHORT).show()
//
//        if (current_User != null) {
//            user_id = current_User!!.uid
//            Toast.makeText(this, "있음 LoginActivity = > "+ current_User!!.email, Toast.LENGTH_SHORT).show()
//            sendToMain()
//        } else {
//            Toast.makeText(this, "LoginActivity = > "+current_User, Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun sendToMain() {
        Toast.makeText(this, "sendToMain = > "+user_email, Toast.LENGTH_SHORT).show()
        val docRef = db!!.collection("Users").document(user_id!!)
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    val user = document!!.toObject(User::class.java)
                    var userName = user!!.name

                    startActivity(Intent(this, MainActivity::class.java))
                    //Toast.makeText(LoginActivity.this, shelterPre, Toast.LENGTH_SHORT).show();
                    if (userName != null) {
                        Toast.makeText(this, "이름 : " + userName, Toast.LENGTH_LONG).show();
                        startActivity(
                            Intent(
                                this,
                                MainActivity::class.java
                            )
                        )
                        finish()
                    } else if (userName == null) {
                        Toast.makeText(this, "이름 에러 : " + userName, Toast.LENGTH_LONG).show();
                        finish()
                    }
                } else {
                    Log.d("LoginActivity => ", "No such document")
                }
            } else {

            }
        }
    }
}