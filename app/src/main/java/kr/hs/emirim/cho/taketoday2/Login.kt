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
    private var currentUser: FirebaseUser? = null
    private var user_email: String? = null
    private var user_id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        currentUser= mAuth!!.currentUser
        val user = mAuth!!.currentUser
        user?.let {
            user_email = user.email
        }

        login_btn.setOnClickListener(View.OnClickListener {
            val loginEmail: String = login_email.getText().toString()
            val loginPass: String = login_password.getText().toString()
            if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)) {
                login_progress.setVisibility(View.VISIBLE)
                mAuth!!.signInWithEmailAndPassword(loginEmail, loginPass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "로그인 성공 :" + user_email,
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this, MainActivity::class.java))
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

        if (currentUser != null) {
            user_id = currentUser!!.uid
            sendToMain()
        } else {
            Toast.makeText(this, "LoginActivity = > null", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendToMain() {
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