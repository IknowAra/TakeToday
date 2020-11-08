package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        val actionBar=supportActionBar
        actionBar?.title=" "

        val drawerToggle:ActionBarDrawerToggle=object:ActionBarDrawerToggle(
            this,
            main_drawer_layout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ){
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }

        drawerToggle.isDrawerIndicatorEnabled=true
        main_drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navigation_view.setNavigationItemSelectedListener(this)    //네비게이션 메뉴 아이템에 클릭 속성 부여
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.cate-> {
                val intent= Intent(this, Category::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }

            R.id.finished-> {
                val intent= Intent(this, Finished::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }

            R.id.setting-> {
                val intent= Intent(this, Setting::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }
            R.id.ask-> {
                val intent= Intent(this, Ask::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
                startActivity(intent)
            }
        }
        main_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}