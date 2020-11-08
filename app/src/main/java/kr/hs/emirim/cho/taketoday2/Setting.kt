package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_setting.*

class Setting : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var db: FirebaseFirestore? = null
    private var user_id: String? = null
    private lateinit var storage: StorageReference
    private lateinit var user_name:String
    private lateinit var user_tele:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        //currentUser= mAuth!!.currentUser
        storage = FirebaseStorage.getInstance().reference

        btn_logout.setOnClickListener{
            mAuth.signOut()
            sendToLogout()
        }

        val user= mAuth.currentUser
        user?.let{
            user_id = user.uid
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

        getNameTele()
    }

    private fun getNameTele() {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Users").document(user_id!!).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {
                    val user = document.toObject(User::class.java)
                    user_name = user!!.name
                    user_tele=user!!.tele

                    userName.text=user_name
                    userTele.text=user_tele
                } else {
                    Log.d("LoginActivity => ", "No such document")
                }
            } else {

            }
        }
    }

    private fun deleteAccount() {
        val docRef = db!!.collection("Users").document(user_id!!)
        val docRef2 = db!!.collection("Posts").document(user_id!!)
       // val desertRef = storage.child("images").child(user_id + ".jpg")

        val user = FirebaseAuth.getInstance().currentUser
        user?.delete()
            ?.addOnCompleteListener {
                docRef.delete()
                    .addOnSuccessListener {
                        docRef2.delete()
                            .addOnSuccessListener {
                                Toast.makeText(this, "Users&Posts 컬렉션 삭제 성공!", Toast.LENGTH_LONG).show()
                            }
                            .addOnFailureListener{
                                Toast.makeText(this, "Posts 컬렉션 삭제 실패", Toast.LENGTH_LONG).show()
                            }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "User &Posts 컬렉션 삭제 실패", Toast.LENGTH_LONG).show()
                    }
                sendToLogout()
            }
            ?.addOnFailureListener {
                Toast.makeText(this, "계정&Post  삭제 실패", Toast.LENGTH_LONG).show()
            }
    }

    private fun sendToLogout() {
        finish()
        var intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}