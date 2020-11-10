package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_category.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class Category : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth;
    private var user_id: String? = null
    private var count: Int = 0
    private var nowStr:String = ""
    private var timeStamp:String=""
    var start:Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        mAuth = FirebaseAuth.getInstance()
        val user= mAuth.currentUser

        user?.let{
            user_id = user.uid
        }
        btn_back.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish();
        }

        button5.setOnClickListener{
            addCode("CpQvOIxECiiUko2Ip45p")
        }
        button7.setOnClickListener{
            addCode("DR0HPx1oD8CAGku7fCJB")
        }
        button6.setOnClickListener{
            addCode("PGIX2mtgFmuuWJQz8GWt")
        }
        button8.setOnClickListener{
            addCode("a9j65Uxcz2iuuGpXgPQv")
        }
        button3.setOnClickListener{
            addCode("iXHQ6h67Xm87TEkMiNp5")
        }
        button4.setOnClickListener{
            addCode("owINC3MNVxPz9BquNdcL")
        }
        button2.setOnClickListener{
            addCode("r7lV0RqNsgFuD8mdJdEe")
        }
        button9.setOnClickListener {
            Toast.makeText(this, "준비중입니다", Toast.LENGTH_SHORT).show()
        }

    }

    private fun addCode(code:String){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val random = Random()

        db.collection("Users").document(user_id.toString()).get().addOnSuccessListener { document ->
            nowStr = ""+document.data?.get(key = "current")

            if(code in nowStr == true){
                showDialog(code)
            }else{
                var ex = document.data?.get(key = "current") as List<String>
                count = ex.size
                if(count>=4){
                    Toast.makeText(this, "4가지 이상 선택하실 수 없습니다", Toast.LENGTH_SHORT).show()
                }else{
                    start=System.currentTimeMillis()

                    val num = random.nextInt(20)
                    val todays = hashMapOf(
                            "cate" to code,
                            "now" to num,
                            "remain" to listOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19),
                            "time" to start.toString().toLong(),
                            "user" to user_id.toString()
                    )
                    db.collection("Todays").document().set(todays)
                            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                                if (task.isSuccessful) {
                                    db.collection("Users").document(user_id.toString()).update("current",FieldValue.arrayUnion(code))
                                    Toast.makeText(this, "추가되었습니다", Toast.LENGTH_SHORT).show()
                                } else {
                                    val error = task.exception!!.message
                                    Toast.makeText(
                                            this,
                                            "Firestore Error : $error",
                                            Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d("에러 : ", error!!)
                                }
                            })
                }
            }
        }
    }


    private fun delCode(code: String){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Users").document(user_id.toString()).update("current",FieldValue.arrayRemove(code))
        db.collection("Todays").whereEqualTo("cate",code).whereEqualTo("user",user_id).get().addOnSuccessListener {documents ->
            for (document in documents){
                db.collection("Todays").document(document.id).delete()
            }

        }
    }

    private fun showDialog(code: String) {
        val dialog =
                AlertDialog.Builder(this)
                        .setMessage("도전을 취소하시겠습니까? (지금까지의 데이터가 모두 손실됩니다)")
                        .setPositiveButton("예") { dialog, which -> delCode(code)
                            Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("아니요") { dialog, which -> }
                        .create()
        dialog.show()
    }

}