package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_setting.*

class Setting : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var db: FirebaseFirestore? = null
    private var currentUser: FirebaseUser? = null
    private var user_id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        currentUser= mAuth!!.currentUser

        btn_login.setOnClickListener{
            mAuth?.signOut()
            sendToLogin()
        }

        btn_remove.setOnClickListener {
            deleteAccountClicked()
        }
    }

    private fun deleteAccountClicked() {
        val dialog =
            AlertDialog.Builder(this)
                .setMessage("정말 계정을 삭제하시겠습니까?")
                .setPositiveButton(
                    "예"
                ) { dialog, which -> deleteAccount() }
                .setNegativeButton(
                    "아니요"
                ) { dialog, which ->
                    Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                }
                .create()
        dialog.show()
    }

    override fun onStart() {
        super.onStart()

        Toast.makeText(this, "email : "+currentUser?.email, Toast.LENGTH_LONG).show()
        if (currentUser != null) {
            user_id = currentUser!!.uid
        } else {
        }
    }

    private fun deleteAccount() {
        val docRef = db!!.collection("Users").document(user_id!!)
        val docRef2 = db!!.collection("Posts").document(user_id!!)
        val user = FirebaseAuth.getInstance().currentUser
        user?.delete()
            ?.addOnCompleteListener {
                docRef.delete()
                    .addOnSuccessListener {
                        docRef2.delete()
                            .addOnSuccessListener {
                                Toast.makeText(this, "계정&컬렉션 삭제 성공!", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this, Login::class.java))
                            }
                            .addOnFailureListener{
                                Toast.makeText(this, "Posts 컬렉션 삭제 실패", Toast.LENGTH_LONG).show()
                            }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "User 컬렉션 삭제 실패", Toast.LENGTH_LONG).show()
                    }
            }
            ?.addOnFailureListener {
                Toast.makeText(this, "계정&컬렉션 삭제 실패", Toast.LENGTH_LONG).show()
            }
    }

    private fun sendToLogin() {
        startActivity(Intent(this, Login::class.java))
    }
}