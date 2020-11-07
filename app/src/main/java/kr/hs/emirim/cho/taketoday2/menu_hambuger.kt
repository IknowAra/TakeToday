package kr.hs.emirim.cho.taketoday2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_menu_hambuger.*

class menu_hambuger : AppCompatActivity() {

    private lateinit var user_name:String
    private lateinit var user_tele:String
    private lateinit var user_id:String
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_hambuger)

        mAuth=FirebaseAuth.getInstance()
        val current_User=mAuth.currentUser
        current_User?.let{
            user_id=current_User.uid
        }
    }

    @Override
    override fun onStart() {
        super.onStart()
        getNameTele()
    }

    private fun getNameTele() {
        Toast.makeText(this, "와왕", Toast.LENGTH_SHORT).show()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Users").document(user_id).get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val documet=task.result
                    if(documet!=null){
                        Log.d("dfdfdd", "DocumentSnapshot data: " + task.result!!.data)
                    }
                }

            }
    }
}