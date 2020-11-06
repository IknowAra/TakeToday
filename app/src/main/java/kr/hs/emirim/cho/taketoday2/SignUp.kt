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
import kotlinx.android.synthetic.main.activity_sign_up.*
class SignUp : AppCompatActivity() {

    private lateinit var userEmail:String;
    private lateinit var userPass:String;
    private lateinit var confirmPass:String;

    private lateinit var mAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()

        btn_sign.setOnClickListener {
            userEmail=email.text.toString()
            userPass=password.text.toString()
            confirmPass=confirm_password.text.toString()
            if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPass) && !TextUtils.isEmpty(
                    confirmPass
                )
            ) {
                if (userPass == confirmPass) {
                    reg_progress.visibility = View.VISIBLE
                    mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                        .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "회원가입 완료",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(
                                    Intent(this, UserSetActivity::class.java)
                                )
                                finish()
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
}