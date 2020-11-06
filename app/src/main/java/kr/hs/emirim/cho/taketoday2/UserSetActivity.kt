//package kr.hs.emirim.cho.taketoday2
//
//import android.content.Intent
//import android.os.Bundle
//import android.text.TextUtils
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.tasks.OnCompleteListener
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.android.synthetic.main.activity_user_set.*
//import java.util.*
//
//class UserSetActivity : AppCompatActivity() {
//
//    private lateinit var user_name:String;
//    private lateinit var user_tele:String;
//    //private lateinit var user_email:String;
//    private var current_user: FirebaseUser? = null
//    private var user_id: String? = null
//    private var user_email: String? = null
//
//    private lateinit var mAuth: FirebaseAuth;
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_user_set)
//
//        mAuth = FirebaseAuth.getInstance()
//        current_user= mAuth.currentUser
//
//        setup_btn.setOnClickListener {
//            user_name=userName.text.toString()
//            user_tele=userTele.text.toString()
//
//                user_email = current_user!!.email
//                user_id=current_user!!.uid
//
//
//            if (!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(user_tele)) {
//                adduser()
//            }
//        }
//    }
//
//    private fun adduser() {
//
//        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
//        val user = User(user_name, user_tele)
//
//        val userMap = hashMapOf(
//                "email" to user_email.toString(),
//                "name" to user.getName(),
//                "tele" to user.getTele(),
//                "current" to listOf<String>(),
//                "finish" to listOf<String>()
//        )
//
//        Toast.makeText(this, user_id.toString(), Toast.LENGTH_SHORT).show()
//        db.collection("Users").document(user_id.toString()).set(userMap)
//                .addOnCompleteListener(OnCompleteListener<Void?> { task ->
//                    if (task.isSuccessful) {
//                        if(current_user!!.isEmailVerified) {
//                            Toast.makeText(this, "유저 정보가 등록됨", Toast.LENGTH_SHORT)
//                                .show()
//                            startActivity(Intent(this, Login::class.java))
//                            finish()
//                        }else{
//                            Toast.makeText(this, "이메일 링크 확인 바람", Toast.LENGTH_LONG)
//                                .show()
//                            current_user?.delete()
//                                ?.addOnCompleteListener {
//                                    task ->
//                                    if(task.isSuccessful){
//                                        Toast.makeText(this, "auth 사용자 삭제", Toast.LENGTH_SHORT)
//
//                                    }
//                                }
//                        }
//                    } else {
//                        val error = task.exception!!.message
//                        Toast.makeText(
//                                this,
//                                "Firestore Error : $error",
//                                Toast.LENGTH_SHORT
//                        ).show()
//                        Log.d("에러 : ", error!!)
//                    }
//                })
//
//    }
//
//}