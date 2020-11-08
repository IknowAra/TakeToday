package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private var user_id: String? = null
    private lateinit var mAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        val user= mAuth.currentUser
        user?.let{
            user_id = user.uid
        }

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var count:Int = 0
        db.collection("Users").document(user_id.toString()).get().addOnSuccessListener { document ->
            var ex = (""+document.data?.get(key = "current")).split(",").toMutableList()
            ex[0] = ex[0].substring(1,ex[0].length)
            ex[ex.size-1] = ex[ex.size-1].substring(0,ex[ex.size-1].length-1)
            ex[0].trim()

            if(ex[0].equals("")){
                count = 0
                button_1.setText("+")
                button_2.setText("")
                button_3.setText("")
                button_4.setText("")
            } else{
                count = ex.size
                button_1.setText("")
                button_2.setText("")
                button_3.setText("")
                button_4.setText("")

                if(count==0){
                    button_1.setText("+")
                }else if(count == 1){
                    button_2.setText("+")
                }else if(count == 2){
                    button_3.setText("+")
                }else if(count == 3){
                    button_4.setText("+")
                }
                for(i in 0..count-1){
                    ex[i] = ex[i].trim()
                    db.collection("Category").document(ex[i]).get().addOnSuccessListener { document ->
                        if(i==0){
                            button_1.setText((document.data?.get(key = "name")).toString())
                        }else if(i == 1){
                            button_2.setText((document.data?.get(key = "name")).toString())
                        }else if(i == 2){
                            button_3.setText((document.data?.get(key = "name")).toString())
                        }else if(i == 3){
                            button_4.setText((document.data?.get(key = "name")).toString())
                        }
                    }
                }
            }
        }

        button_1.setOnClickListener{
            if((button_1.text).equals("+")){
                startActivity(Intent(this, Category::class.java))
            }else if((button_1.text).equals("") || (button_1.text).equals(null)){
                // null
            }else{
                moveToGallery(0)
            }
        }
        button_2.setOnClickListener{
            if((button_2.text).equals("+")){
                val intent=Intent(this, Category::class.java)
                startActivity(intent)
            }else if((button_2.text).equals("") || (button_1.text).equals(null)){
                // null
            }else{
                moveToGallery(1)
            }
        }
        button_3.setOnClickListener{
            if((button_3.text).equals("+")){
                val intent=Intent(this, Category::class.java)
                startActivity(intent)
            }else if((button_3.text).equals("") || (button_1.text).equals(null)){
                // null
            }else{
                moveToGallery(2)
            }
        }
        button_4.setOnClickListener{
            if((button_4.text).equals("+")){
                val intent=Intent(this, Category::class.java)
                startActivity(intent)
            } else if((button_4.text).equals("") || (button_1.text).equals(null)){
                // null
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
                val intent=Intent(this, Category::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }

            R.id.finished-> {
                val intent=Intent(this, Finished::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }

            R.id.setting-> {
                val intent=Intent(this, Setting::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }
            R.id.ask-> {
                val intent=Intent(this, Ask::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
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
            var ex = (""+document.data?.get(key = "current")).split(",").toMutableList()
            ex[0] = ex[0].substring(1,ex[0].length)
            ex[ex.size-1] = ex[ex.size-1].substring(0,ex[ex.size-1].length-1)
            var code = ex[num].trim()

            val intent=Intent(this, galleryActivity::class.java)
            intent.putExtra("code",code)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        mAuth = FirebaseAuth.getInstance()
        val user= mAuth.currentUser
        user?.let{
            user_id = user.uid
        }

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var count:Int = 0
        db.collection("Users").document(user_id.toString()).get().addOnSuccessListener { document ->
            var ex = (""+document.data?.get(key = "current")).split(",").toMutableList()
            ex[0] = ex[0].substring(1,ex[0].length)
            ex[ex.size-1] = ex[ex.size-1].substring(0,ex[ex.size-1].length-1)
            ex[0].trim()

            if(ex[0].equals("")){
                count = 0
                button_1.setText("+")
                button_2.setText("")
                button_3.setText("")
                button_4.setText("")
            } else{
                count = ex.size
                button_1.setText("")
                button_2.setText("")
                button_3.setText("")
                button_4.setText("")

                if(count==0){
                    button_1.setText("+")
                }else if(count == 1){
                    button_2.setText("+")
                }else if(count == 2){
                    button_3.setText("+")
                }else if(count == 3){
                    button_4.setText("+")
                }
                for(i in 0..count-1){
                    ex[i] = ex[i].trim()
                    db.collection("Category").document(ex[i]).get().addOnSuccessListener { document ->
                        if(i==0){
                            button_1.setText((document.data?.get(key = "name")).toString())
                        }else if(i == 1){
                            button_2.setText((document.data?.get(key = "name")).toString())
                        }else if(i == 2){
                            button_3.setText((document.data?.get(key = "name")).toString())
                        }else if(i == 3){
                            button_4.setText((document.data?.get(key = "name")).toString())
                        }
                    }
                }
            }
        }

        button_1.setOnClickListener{
            if((button_1.text).equals("+")){
                startActivity(Intent(this, Category::class.java))
            }else if((button_1.text).equals("")){
                // null
            }else{
                moveToGallery(0)
            }
        }
        button_2.setOnClickListener{
            if((button_2.text).equals("+")){
                val intent=Intent(this, Category::class.java)
                startActivity(intent)
            }else if((button_2.text).equals("")){
                // null
            }else{
                moveToGallery(1)
            }
        }
        button_3.setOnClickListener{
            if((button_3.text).equals("+")){
                val intent=Intent(this, Category::class.java)
                startActivity(intent)
            }else if((button_3.text).equals("")){
                // null
            }else{
                moveToGallery(2)
            }
        }
        button_4.setOnClickListener{
            if((button_4.text).equals("+")){
                val intent=Intent(this, Category::class.java)
                startActivity(intent)
            } else if((button_4.text).equals("")){
                // null
            }else{
                moveToGallery(3)
            }
        }


        btn_open.setOnClickListener{
            drawer_layout.openDrawer(GravityCompat.END)
        }

        naviView.setNavigationItemSelectedListener(this)    //네비게이션 메뉴 아이템에 클릭 속성 부여
    }


}