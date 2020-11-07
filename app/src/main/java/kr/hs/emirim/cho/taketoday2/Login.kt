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
    private lateinit var mAuth: FirebaseAuth;
    private var current_User: FirebaseUser? = null
    private var user_email: String?=null
    private var user_id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        login_btn.setOnClickListener(View.OnClickListener {
            current_User= mAuth.currentUser
            user_email=current_User?.email
            Log.d("#)(*(#$)*#(*$)(============>>>>>>", user_email.toString())
            val loginEmail: String = login_email.getText().toString()
            val loginPass: String = login_password.getText().toString()
            if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)) {
                login_progress.setVisibility(View.VISIBLE)
                mAuth.signInWithEmailAndPassword(loginEmail, loginPass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                current_User= mAuth.currentUser
                                Log.d("",current_User.toString())

                                if(current_User != null && current_User!!.isEmailVerified){
                                    current_User= mAuth.currentUser
                                    user_email=current_User?.email
                                    Toast.makeText(
                                            this,
                                            "로그인 성공 :" + user_email,
                                            Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                }else{
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

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "hihihihi", Toast.LENGTH_SHORT).show()

        current_User= mAuth.currentUser
        user_email=current_User?.email

        if (current_User != null && current_User!!.isEmailVerified) {
            current_User= mAuth.currentUser
            user_email=current_User?.email
            user_id = current_User!!.uid
            sendToMain()
        } else {
            Toast.makeText(this, "LoginActivity = > "+current_User, Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendToMain() {
        db!!.collection("Users").document(user_id!!).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    val user = document!!.toObject(User::class.java)
                    var userName = user!!.name

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    //Toast.makeText(LoginActivity.this, shelterPre, Toast.LENGTH_SHORT).show();
                    if (userName != null) {
                        Toast.makeText(this, "이름 : " + userName, Toast.LENGTH_LONG).show();
                        startActivity(Intent(this, MainActivity::class.java))
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

    override fun onResume() {
        super.onResume()
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        login_btn.setOnClickListener(View.OnClickListener {
            current_User= mAuth.currentUser
            user_email=current_User?.email
            Log.d("#)(*(#$)*#(*$)(============>>>>>>", user_email.toString())
            val loginEmail: String = login_email.getText().toString()
            val loginPass: String = login_password.getText().toString()
            if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)) {
                login_progress.setVisibility(View.VISIBLE)
                mAuth.signInWithEmailAndPassword(loginEmail, loginPass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                current_User= mAuth.currentUser

                                if(current_User != null && current_User!!.isEmailVerified){
                                    current_User= mAuth.currentUser
                                    user_email=current_User?.email
                                    Toast.makeText(
                                            this,
                                            "로그인 성공 :" + user_email,
                                            Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                }else{
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
}