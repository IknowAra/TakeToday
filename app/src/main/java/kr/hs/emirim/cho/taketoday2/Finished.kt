package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_category.btn_back
import kotlinx.android.synthetic.main.activity_finished.*

class Finished : AppCompatActivity() {

    private var user_id: String? = null
    private lateinit var mAuth: FirebaseAuth
    var remain: List<Int> = listOf()
    var name: MutableList<String> = ArrayList()
    var code: String = ""
    var arr: MutableList<String> = mutableListOf()
    var buttons:List<Button> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished)
        buttons = arrayListOf<Button>(findViewById(R.id.fbutton1),findViewById(R.id.fbutton2),findViewById(R.id.fbutton3),findViewById(R.id.fbutton4),findViewById(R.id.fbutton5),findViewById(R.id.fbutton6),findViewById(R.id.fbutton7),findViewById(R.id.fbutton8))

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        remain = listOf()
        user?.let {
            user_id = user.uid
        }

        btn_back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        for (btn in buttons) {
            btn.isEnabled = false
            btn.visibility = View.INVISIBLE
        }

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Todays").whereEqualTo("user", user_id).get().addOnSuccessListener { docus ->
            for (d in docus) {
                if((d.data?.get(key = "remain")).toString() == "[]"){
                    arr.add((d.data?.get(key = "cate")).toString())
                }
            }
            for ((ix, a) in arr.withIndex()) {
                db.collection("Category").document(a).get().addOnSuccessListener { doc ->
                    buttons[ix].isEnabled = true
                    buttons[ix].visibility = View.VISIBLE
                    buttons[ix].text = (doc.data?.get(key = "name")).toString()
                }
            }
        }

        fbutton1.setOnClickListener {
            moveToGallery(arr[0])
        }

        fbutton2.setOnClickListener {
            moveToGallery(arr[1])
        }

        fbutton3.setOnClickListener {
            moveToGallery(arr[2])
        }

        fbutton4.setOnClickListener {
            moveToGallery(arr[3])
        }

        fbutton5.setOnClickListener {
            moveToGallery(arr[4])
        }

        fbutton6.setOnClickListener {
            moveToGallery(arr[5])
        }

        fbutton7.setOnClickListener {
            moveToGallery(arr[6])
        }

    }


    private fun moveToGallery(code: String) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
            db.collection("Users").document(user_id.toString()).update("inCate", code)
            startActivity(Intent(this, galleryActivity::class.java))
        }

}