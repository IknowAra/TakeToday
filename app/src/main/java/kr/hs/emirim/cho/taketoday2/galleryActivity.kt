package kr.hs.emirim.cho.taketoday2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.DrawableContainer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_category.btn_back
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.activity_popup_post.*
import java.util.*

class galleryActivity : AppCompatActivity() {

    private var user_id: String? = null
    private lateinit var mAuth: FirebaseAuth;
    var array=Array<String>(20, { ' '.toString() })
    var tag:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        mAuth = FirebaseAuth.getInstance()
        val user= mAuth.currentUser
        user?.let{
            user_id = user.uid
        }

        if (intent.hasExtra("name")) {
            cate_title.setText(intent.getStringExtra("name"))
        }

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Category").whereEqualTo("name",intent.getStringExtra("name")).get().addOnSuccessListener { documents ->
            for (document in documents) {
                var a:List<String> = document.data.get(key = "arr") as List<String>
                tag = a.random()
                todays_tag.setText("오늘의 주제 : "+tag)
            }

        }



        fun <T> List<T>.random() : T {
            val random = Random().nextInt((size))
            return get(random)
        }


        btn_back.setOnClickListener{
            finish();
        }

        todays_tag.setOnClickListener {
            startActivity(Intent(this, Upload::class.java))
        }
        
        btn_1.setOnClickListener {
            var dialog = Popup_post()

            dialog.show(supportFragmentManager, "customDialog")
        }
     }
}