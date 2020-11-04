package kr.hs.emirim.cho.taketoday2

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
    val TAG = "YOUR-TAG-NAME"
    private lateinit var mAuth: FirebaseAuth;
    private var user_id: String? = null
    private var count: Int = 0


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

            if(isin("CpQvOIxECiiUko2Ip45p")){
                showDialog("CpQvOIxECiiUko2Ip45p")
            }else{
                addCode("CpQvOIxECiiUko2Ip45p")
            }
        }
        button7.setOnClickListener{
            if(isin("DR0HPx1oD8CAGku7fCJB")){
                showDialog("DR0HPx1oD8CAGku7fCJB")
            }else{
                addCode("DR0HPx1oD8CAGku7fCJB")
            }
        }
        button6.setOnClickListener{
            if(isin("PGIX2mtgFmuuWJQz8GWt")){
                showDialog("PGIX2mtgFmuuWJQz8GWt")
            }else{
                addCode("PGIX2mtgFmuuWJQz8GWt")
            }
        }
        button8.setOnClickListener{
            if(isin("a9j65Uxcz2iuuGpXgPQv")){
                showDialog("a9j65Uxcz2iuuGpXgPQv")
            }else{
                addCode("a9j65Uxcz2iuuGpXgPQv")
            }
        }
        button3.setOnClickListener{
            if(isin("iXHQ6h67Xm87TEkMiNp5")){
                showDialog("iXHQ6h67Xm87TEkMiNp5")
            }else{
                addCode("iXHQ6h67Xm87TEkMiNp5")
            }
        }
        button4.setOnClickListener{
            if(isin("owINC3MNVxPz9BquNdcL")){
                showDialog("owINC3MNVxPz9BquNdcL")
            }else{
                addCode("owINC3MNVxPz9BquNdcL")
            }
        }
        button2.setOnClickListener{
            if(isin("r7lV0RqNsgFuD8mdJdEe")){
                showDialog("r7lV0RqNsgFuD8mdJdEe")
            }else {
                addCode("r7lV0RqNsgFuD8mdJdEe")
            }
        }




    }

    private fun addCode(code:String){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("Users").document(user_id.toString()).get().addOnSuccessListener { document ->
            var ex = (""+document.data?.get(key = "current")).split(",")
            count = ex.size
        }
        if(count>=4){
            Toast.makeText(this, "4가지 이상 선택하실 수 없습니다", Toast.LENGTH_SHORT).show()
        }else{
            db.collection("Users").document(user_id.toString()).update("current",FieldValue.arrayUnion(code))
        }

    }
    private fun isin(code:String):Boolean{
        var result:Boolean = true
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Users").document(user_id.toString()).get().addOnSuccessListener { document ->
            var nowStr = ""+document.data?.get(key = "current")
            if(nowStr.contains(code)){
                result = true
            }else{
                result = false
            }
        }
        return result
    }

    private fun delCode(code: String){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("Users").document(user_id.toString()).update("current",FieldValue.arrayRemove(code))

    }

    private fun showDialog(code: String) {
        val dialog =
                AlertDialog.Builder(this)
                        .setMessage("도전을 취소하시겠습니까? (지금까지의 데이터가 모두 손실됩니다)")
                        .setPositiveButton(
                                "예"
                        ) { dialog, which -> delCode(code) }
                        .setNegativeButton(
                                "아니요"
                        ) { dialog, which ->
                        }
                        .create()
        dialog.show()
    }

}