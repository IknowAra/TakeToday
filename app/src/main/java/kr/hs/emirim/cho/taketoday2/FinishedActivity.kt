package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_category.btn_back
import kotlinx.android.synthetic.main.activity_finished.*


class FinishedActivity : AppCompatActivity() {

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
        buttons = arrayListOf<Button>(fbutton1,fbutton2,fbutton3,fbutton4,fbutton5,fbutton6,fbutton7,fbutton8)

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
                else{
                    showText()
                    break
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

    private fun showText() {
        val textView = TextView(this)
        textView.text = "완성된 갤러리가 없습니다"
        textView.gravity = Gravity.CENTER_HORIZONTAL
        textView.setTextColor(getResources().getColor(R.color.black))
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 23F)
        llayout.addView(textView)
    }


    private fun moveToGallery(code: String) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
            db.collection("Users").document(user_id.toString()).update("inCate", code)
            startActivity(Intent(this, galleryActivity::class.java))
        }

}