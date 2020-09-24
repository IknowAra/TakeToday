package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_open.setOnClickListener{
            drawer_layout.openDrawer(GravityCompat.END)
        }
        naviView.setNavigationItemSelectedListener(this)    //네비게이션 메뉴 아이템에 클릭 속성 부여
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.cate-> {
                intent=Intent(this, Category::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }

            R.id.finished-> {
                intent=Intent(this, Finished::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }

            R.id.setting-> {
                intent=Intent(this, Setting::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }
            R.id.ask-> {
                intent=Intent(this, Ask::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
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
}