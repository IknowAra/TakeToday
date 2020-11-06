package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.android.synthetic.main.activity_category.*
import java.util.*

class Category : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth;
    private var user_id: String? = null
    private var count: Int = 0
    private var nowStr:String = ""


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

    }

    private fun addCode(code:String){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Users").document(user_id.toString()).get().addOnSuccessListener { document ->
            nowStr = ""+document.data?.get(key = "current")
            if(code in nowStr == true){
                showDialog(code)
            }else{
                var ex = (""+document.data?.get(key = "current")).split(",")
                count = ex.size
                if(count>=4){
                    Toast.makeText(this, "4가지 이상 선택하실 수 없습니다", Toast.LENGTH_SHORT).show()
                }else{
                    db.collection("Users").document(user_id.toString()).update("current",FieldValue.arrayUnion(code))

                    Toast.makeText(this, "추가되었습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun delCode(code: String){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Users").document(user_id.toString()).update("current",FieldValue.arrayRemove(code))
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