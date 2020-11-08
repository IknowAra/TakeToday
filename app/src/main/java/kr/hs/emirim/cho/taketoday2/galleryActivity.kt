package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_category.btn_back
import kotlinx.android.synthetic.main.activity_gallery.*
import java.util.*

class galleryActivity : AppCompatActivity() {


    var buttons: ArrayList<Button> = arrayListOf<Button>()
    private var user_id: String? = null
    private lateinit var mAuth: FirebaseAuth;
    private lateinit var storageReference: StorageReference
    var tag:String = ""
    var code:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        buttons = arrayListOf<Button>(findViewById(R.id.btn_1),findViewById(R.id.btn_2),findViewById(R.id.btn_3),findViewById(R.id.btn_4),findViewById(R.id.btn_5),findViewById(R.id.btn_6),findViewById(R.id.btn_7),findViewById(R.id.btn_8),findViewById(R.id.btn_9),findViewById(R.id.btn_10),findViewById(R.id.btn_11),findViewById(R.id.btn_12),findViewById(R.id.btn_13),findViewById(R.id.btn_14),findViewById(R.id.btn_15),findViewById(R.id.btn_16),findViewById(R.id.btn_17),findViewById(R.id.btn_18),findViewById(R.id.btn_19),findViewById<Button>(R.id.btn_20))

        mAuth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        val user= mAuth.currentUser
        user?.let{
            user_id = user.uid
        }


        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("Users").document(user_id.toString()).get().addOnSuccessListener { document ->
            code = document.data?.get(key = "inCate").toString()

            db.collection("Category").document(code).get().addOnSuccessListener { document ->
                var name:String = document.data?.get(key = "name") as String
                var arr:List<String> = document.data?.get(key = "arr") as List<String>
                cate_title.setText(name)
                db.collection("Todays").whereEqualTo("cate",code).whereEqualTo("user",user_id).get().addOnSuccessListener { documents ->
                    for(docu in documents){
                        var now:Int = (docu.data?.get(key = "now")).toString().toInt()
                        var remain:List<String> = docu.data?.get(key = "remain") as List<String>
                        todays_tag.setText("오늘의 주제 : "+arr[now])
                        var butt:String = "btn_"+(now+1).toString()

//                    for ((index,btn) in buttons.withIndex()){
//
//                        if(index.toString() in remain){
//                            btn
//                        }else{
//                            btn.setBackgroundResource(R.drawable.ic_baseline_menu_50);
//                        }
//                    }
                    }
                }
            }
        }


        todays_tag.setOnClickListener {
            var intent = Intent(this, Upload::class.java)
            startActivity(intent)
        }


        btn_back.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        
        btn_1.setOnClickListener {
            var dialog = Popup_post()
            dialog.show(supportFragmentManager, "customDialog")
        }
     }

    public fun makeRandom(){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Todays").whereEqualTo("cate",code).whereEqualTo("user",user_id).get().addOnSuccessListener { documents ->
            for(document in documents){
                var now = document.data?.get(key = "now") as Int
                var a:List<Int> = document.data?.get(key = "remain") as List<Int>
                if(a.size == 1){
                    val dialog =
                            AlertDialog.Builder(this)
                                    .setMessage("너무 어려운 주제였나요? 마지막 미션은 자유로 해도 좋아요 :)")
                                    .setPositiveButton("네") { dialog, which ->
                                        Toast.makeText(this, "화이팅!", Toast.LENGTH_SHORT).show()
                                    }
                                    .create()
                    dialog.show()
                    break
                }
                var mu:MutableList<Int> = a.toMutableList()
                mu.remove(now)
                var result = mu.random()
                db.collection("Todays").document(document.id).update("now",result)
            }
        }
        fun <T> List<T>.random() : T {
            val random = Random().nextInt((size))
            return get(random)
        }
    }

    override fun onResume() {
        super.onResume()
        mAuth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        val user= mAuth.currentUser
        user?.let{
            user_id = user.uid
        }


        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("Users").document(user_id.toString()).get().addOnSuccessListener { document ->
            code = document.data?.get(key = "inCate").toString()

            db.collection("Category").document(code).get().addOnSuccessListener { document ->
                var name:String = document.data?.get(key = "name") as String
                var arr:List<String> = document.data?.get(key = "arr") as List<String>
                cate_title.setText(name)
                db.collection("Todays").whereEqualTo("cate",code).whereEqualTo("user",user_id).get().addOnSuccessListener { documents ->
                    for(docu in documents){
                        var now:Int = (docu.data?.get(key = "now")).toString().toInt()
                        var remain:List<String> = docu.data?.get(key = "remain") as List<String>
                        todays_tag.setText("오늘의 주제 : "+arr[now])
                        var butt:String = "btn_"+(now+1).toString()

//                    for ((index,btn) in buttons.withIndex()){
//
//                        if(index.toString() in remain){
//                            btn
//                        }else{
//                            btn.setBackgroundResource(R.drawable.ic_baseline_menu_50);
//                        }
//                    }
                    }
                }
            }
        }

        todays_tag.setOnClickListener {
            var intent = Intent(this, Upload::class.java)
            intent.putExtra("code",code)
            startActivity(intent)
        }

        btn_1.setOnClickListener {
            var dialog = Popup_post()

            dialog.show(supportFragmentManager, "customDialog")
        }
    }
}