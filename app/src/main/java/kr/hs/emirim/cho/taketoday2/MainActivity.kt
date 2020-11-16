package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private var user_id: String? = null
    private lateinit var mAuth: FirebaseAuth;
    private lateinit var ex:MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btnArray:List<Button> = arrayListOf<Button>(findViewById(R.id.button_1),findViewById(R.id.button_2),findViewById(R.id.button_3),findViewById(R.id.button_4))
        mAuth = FirebaseAuth.getInstance()
        val user= mAuth.currentUser
        user?.let{
            user_id = user.uid
        }
        for (b in btnArray){
            b.visibility = View.INVISIBLE
            b.setBackgroundResource(R.drawable.pola)
        }


        button_1.setOnClickListener{
            if((button_1.text).equals("")){
                startActivity(Intent(this, CategoryActivity::class.java))
            }else{
                moveToGallery(0)
            }
        }
        button_2.setOnClickListener{
            if((button_2.text).equals("")){
                startActivity(Intent(this, CategoryActivity::class.java))
            }else{
                moveToGallery(1)
            }
        }
        button_3.setOnClickListener{
            if((button_3.text).equals("")){
                startActivity(Intent(this, CategoryActivity::class.java))
            }else{
                moveToGallery(2)
            }
        }
        button_4.setOnClickListener{
            if((button_4.text).equals("")){
                startActivity(Intent(this, CategoryActivity::class.java))
            }else{
                moveToGallery(3)
            }
        }


        btn_open.setOnClickListener{
            drawer_layout.openDrawer(GravityCompat.END)
        }

        naviView.setNavigationItemSelectedListener(this)    //네비게이션 메뉴 아이템에 클릭 속성 부여
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.cate-> {
                val intent=Intent(this, CategoryActivity::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }

            R.id.finished-> {
                val intent=Intent(this, FinishedActivity::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }

            R.id.setting-> {
                val intent=Intent(this, SettingActivity::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }
            R.id.ask-> {
                val intent=Intent(this, AskActivity::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }
        }
        //drawer_layout.closeDrawers()
        return false
    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.END))
        {
            drawer_layout.closeDrawers()
        }
        else{   
            super.onBackPressed()
        }
    }

    private fun moveToGallery(num:Int){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Users").document(user_id.toString()).get().addOnSuccessListener { document ->
            var ex = document.data?.get(key = "current") as List<String>
            var code = ex[num]

            db.collection("Users").document(user_id.toString()).update("inCate",code).addOnSuccessListener {
                val intent=Intent(this, galleryActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onResume() {
        super.onResume()

        var btnArray: List<Button> = arrayListOf<Button>(findViewById(R.id.button_1), findViewById(R.id.button_2), findViewById(R.id.button_3), findViewById(R.id.button_4))
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        user?.let {
            user_id = user.uid
        }
        for (b in btnArray) {
            b.visibility = View.INVISIBLE
            b.setBackgroundResource(R.drawable.pola)
        }


        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var count: Int = 0
        db.collection("Users").document(user_id.toString()).get().addOnSuccessListener { document ->
            ex = document.data?.get(key = "current") as MutableList<String>
            for ((idx, co) in ex.withIndex()) {
                db.collection("Category").document(co).get().addOnSuccessListener { d ->
                    btnArray[idx].text = (d.data?.get(key = "name")).toString()
                    btnArray[idx].visibility = View.VISIBLE
                }
            }
            if (ex.size != 4) {
                btnArray[ex.size].text = ""
                btnArray[ex.size].visibility = View.VISIBLE
                btnArray[ex.size].setBackgroundResource(R.drawable.take)
            }

        }

        button_1.setOnClickListener {
            if ((button_1.text).equals("")) {
                startActivity(Intent(this, CategoryActivity::class.java))
            } else {
                moveToGallery(0)
            }
        }
        button_2.setOnClickListener {
            if ((button_2.text).equals("")) {
                startActivity(Intent(this, CategoryActivity::class.java))
            } else {
                moveToGallery(1)
            }
        }
        button_3.setOnClickListener {
            if ((button_3.text).equals("")) {
                startActivity(Intent(this, CategoryActivity::class.java))
            } else {
                moveToGallery(2)
            }
        }
        button_4.setOnClickListener {
            if ((button_4.text).equals("")) {
                startActivity(Intent(this, CategoryActivity::class.java))
            } else {
                moveToGallery(3)
            }
        }


        btn_open.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.END)
        }

        naviView.setNavigationItemSelectedListener(this)    //네비게이션 메뉴 아이템에 클릭 속성 부여
    }

}