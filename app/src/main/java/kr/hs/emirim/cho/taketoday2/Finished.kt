package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished)

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
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        fbutton1.setOnClickListener {
            if(fbutton1.text=="음식"){
                moveToGallery("CpQvOIxECiiUko2Ip45p")
            }else if(fbutton1.text=="책"){
                moveToGallery("DR0HPx1oD8CAGku7fCJB")
            }else if(fbutton1.text=="미션"){
                moveToGallery("PGIX2mtgFmuuWJQz8GWt")
            }else if(fbutton1.text=="나"){
                moveToGallery("a9j65Uxcz2iuuGpXgPQv")
            }else if(fbutton1.text=="공부"){
                moveToGallery("iXHQ6h67Xm87TEkMiNp5")
            }else if(fbutton1.text=="코로나19"){
                moveToGallery("owINC3MNVxPz9BquNdcL")
            }else if(fbutton1.text=="하늘"){
                moveToGallery("r7lV0RqNsgFuD8mdJdEe")
            }
        }

        fbutton2.setOnClickListener {
            if(fbutton2.text=="음식"){
                moveToGallery("CpQvOIxECiiUko2Ip45p")
            }else if(fbutton2.text=="책"){
                moveToGallery("DR0HPx1oD8CAGku7fCJB")
            }else if(fbutton2.text=="미션"){
                moveToGallery("PGIX2mtgFmuuWJQz8GWt")
            }else if(fbutton2.text=="나"){
                moveToGallery("a9j65Uxcz2iuuGpXgPQv")
            }else if(fbutton2.text=="공부"){
                moveToGallery("iXHQ6h67Xm87TEkMiNp5")
            }else if(fbutton2.text=="코로나19"){
                moveToGallery("owINC3MNVxPz9BquNdcL")
            }else if(fbutton2.text=="하늘"){
                moveToGallery("r7lV0RqNsgFuD8mdJdEe")
            }
        }

        fbutton3.setOnClickListener {
            if(fbutton3.text=="음식"){
                moveToGallery("CpQvOIxECiiUko2Ip45p")
            }else if(fbutton3.text=="책"){
                moveToGallery("DR0HPx1oD8CAGku7fCJB")
            }else if(fbutton3.text=="미션"){
                moveToGallery("PGIX2mtgFmuuWJQz8GWt")
            }else if(fbutton3.text=="나"){
                moveToGallery("a9j65Uxcz2iuuGpXgPQv")
            }else if(fbutton3.text=="공부"){
                moveToGallery("iXHQ6h67Xm87TEkMiNp5")
            }else if(fbutton3.text=="코로나19"){
                moveToGallery("owINC3MNVxPz9BquNdcL")
            }else if(fbutton3.text=="하늘"){
                moveToGallery("r7lV0RqNsgFuD8mdJdEe")
            }
        }

        fbutton4.setOnClickListener {
            if(fbutton4.text=="음식"){
                moveToGallery("CpQvOIxECiiUko2Ip45p")
            }else if(fbutton4.text=="책"){
                moveToGallery("DR0HPx1oD8CAGku7fCJB")
            }else if(fbutton4.text=="미션"){
                moveToGallery("PGIX2mtgFmuuWJQz8GWt")
            }else if(fbutton4.text=="나"){
                moveToGallery("a9j65Uxcz2iuuGpXgPQv")
            }else if(fbutton4.text=="공부"){
                moveToGallery("iXHQ6h67Xm87TEkMiNp5")
            }else if(fbutton4.text=="코로나19"){
                moveToGallery("owINC3MNVxPz9BquNdcL")
            }else if(fbutton4.text=="하늘"){
                moveToGallery("r7lV0RqNsgFuD8mdJdEe")
            }
        }

        fbutton5.setOnClickListener {
            if(fbutton5.text=="음식"){
                moveToGallery("CpQvOIxECiiUko2Ip45p")
            }else if(fbutton5.text=="책"){
                moveToGallery("DR0HPx1oD8CAGku7fCJB")
            }else if(fbutton5.text=="미션"){
                moveToGallery("PGIX2mtgFmuuWJQz8GWt")
            }else if(fbutton5.text=="나"){
                moveToGallery("a9j65Uxcz2iuuGpXgPQv")
            }else if(fbutton5.text=="공부"){
                moveToGallery("iXHQ6h67Xm87TEkMiNp5")
            }else if(fbutton5.text=="코로나19"){
                moveToGallery("owINC3MNVxPz9BquNdcL")
            }else if(fbutton5.text=="하늘"){
                moveToGallery("r7lV0RqNsgFuD8mdJdEe")
            }
        }

        fbutton6.setOnClickListener {
            if(fbutton6.text=="음식"){
                moveToGallery("CpQvOIxECiiUko2Ip45p")
            }else if(fbutton6.text=="책"){
                moveToGallery("DR0HPx1oD8CAGku7fCJB")
            }else if(fbutton6.text=="미션"){
                moveToGallery("PGIX2mtgFmuuWJQz8GWt")
            }else if(fbutton6.text=="나"){
                moveToGallery("a9j65Uxcz2iuuGpXgPQv")
            }else if(fbutton6.text=="공부"){
                moveToGallery("iXHQ6h67Xm87TEkMiNp5")
            }else if(fbutton6.text=="코로나19"){
                moveToGallery("owINC3MNVxPz9BquNdcL")
            }else if(fbutton6.text=="하늘"){
                moveToGallery("r7lV0RqNsgFuD8mdJdEe")
            }
        }

        fbutton7.setOnClickListener {
            if(fbutton7.text=="음식"){
                moveToGallery("CpQvOIxECiiUko2Ip45p")
            }else if(fbutton7.text=="책"){
                moveToGallery("DR0HPx1oD8CAGku7fCJB")
            }else if(fbutton7.text=="미션"){
                moveToGallery("PGIX2mtgFmuuWJQz8GWt")
            }else if(fbutton7.text=="나"){
                moveToGallery("a9j65Uxcz2iuuGpXgPQv")
            }else if(fbutton7.text=="공부"){
                moveToGallery("iXHQ6h67Xm87TEkMiNp5")
            }else if(fbutton7.text=="코로나19"){
                moveToGallery("owINC3MNVxPz9BquNdcL")
            }else if(fbutton7.text=="하늘"){
                moveToGallery("r7lV0RqNsgFuD8mdJdEe")
            }
        }

        var count: Int = 0
        db.collection("Users").document(user_id.toString()).get()
            .addOnSuccessListener { document ->
                var current: List<String> =
                    document.data?.get(key = "current") as List<String>
                for (i in current) {
                    db.collection("Todays").whereEqualTo("cate", i)
                        .whereEqualTo("user", user_id).get()
                        .addOnSuccessListener { documents ->
                            for (docu in documents) {
                                remain = docu.data?.get(key = "remain") as List<Int>
                                if (remain.size == 0) {
                                    count += 1
                                    setBtnText(count, i)
                                }
                            }
                        }
                }
            }


        }

    private fun moveToGallery(code: String) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
            db.collection("Users").document(user_id.toString()).update("inCate", code)
            startActivity(Intent(this, galleryActivity::class.java))
        }

    private fun setBtnText(count: Int, i: String) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Category").document(i).get().addOnSuccessListener { document ->
            var oname = document.data?.get(key = "name") as String
            name.add(oname)

            if (count == 1) {
                fbutton1.setText(name[0])
                fbutton2.setText("")
                fbutton3.setText("")
                fbutton4.setText("")
                fbutton5.setText("")
                fbutton6.setText("")
                fbutton7.setText("")
                fbutton8.setText("")
            } else if (count == 2) {
                fbutton1.setText(name[0])
                fbutton2.setText(name[1])
                fbutton3.setText("")
                fbutton4.setText("")
                fbutton5.setText("")
                fbutton6.setText("")
                fbutton7.setText("")
                fbutton8.setText("")
            } else if (count == 3) {
                fbutton1.setText(name[0])
                fbutton2.setText(name[1])
                fbutton3.setText(name[2])
                fbutton4.setText("")
                fbutton5.setText("")
                fbutton6.setText("")
                fbutton7.setText("")
                fbutton8.setText("")
            } else if (count == 4) {
                fbutton1.setText(name[0])
                fbutton2.setText(name[1])
                fbutton3.setText(name[2])
                fbutton4.setText(name[3])
                fbutton5.setText("")
                fbutton6.setText("")
                fbutton7.setText("")
                fbutton8.setText("")
            } else if (count == 5) {
                fbutton1.setText(name[0])
                fbutton2.setText(name[1])
                fbutton3.setText(name[2])
                fbutton4.setText(name[3])
                fbutton5.setText(name[4])
                fbutton6.setText("")
                fbutton7.setText("")
                fbutton8.setText("")
            } else if (count == 6) {
                fbutton1.setText(name[0])
                fbutton2.setText(name[1])
                fbutton3.setText(name[2])
                fbutton4.setText(name[3])
                fbutton5.setText(name[4])
                fbutton6.setText(name[5])
                fbutton7.setText("")
                fbutton8.setText("")
            } else if (count == 7) {
                fbutton1.setText(name[0])
                fbutton2.setText(name[1])
                fbutton3.setText(name[2])
                fbutton4.setText(name[3])
                fbutton5.setText(name[4])
                fbutton6.setText(name[5])
                fbutton7.setText(name[6])
                fbutton8.setText("")
            } else if (count == 8) {
                fbutton1.setText(name[0])
                fbutton2.setText(name[1])
                fbutton3.setText(name[2])
                fbutton4.setText(name[3])
                fbutton5.setText(name[4])
                fbutton6.setText(name[5])
                fbutton7.setText(name[6])
                fbutton8.setText(name[7])
            }
        }
    }
}