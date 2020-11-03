package kr.hs.emirim.cho.taketoday2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.android.synthetic.main.activity_category.*
import java.util.*

class Category : AppCompatActivity() {
    val TAG = "YOUR-TAG-NAME"
    private lateinit var mAuth: FirebaseAuth;
    private var user_id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        mAuth = FirebaseAuth.getInstance()
        val user= mAuth.currentUser

        user?.let{
            user_id = user.uid
        }
        btn_back.setOnClickListener{
            finish();
        }
    }

    private fun select(){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val userMap: MutableMap<String, Array<String>> = HashMap()
        db.collection("Users").document(user_id.toString()).get(Source.CACHE).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val document = task.result
                Log.d(TAG, "Cached document data: ${document?.data}")
            }
        }
//        userMap[""] =


    }

}