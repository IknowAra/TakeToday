package kr.hs.emirim.cho.taketoday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    var sky=arrayOf("구름" ,"태양", "노을", "새", "밤하늘", "별", "보름달", "비행기", "일출", "먹구름" , "무지개", "반달", "비 오는 하늘",
        "눈 오는 하늘", "구름 없는 하늘", "강하늘", "실내에서 본 하늘", "미세먼지", "숲 속 하늘", "전봇대")

    var study=arrayOf("필기구", "지금 보는 인강", "공부시간", "오늘의 플래너", "나만의 필통", "오늘 수학", "오늘의 필기", "도서관", "오늘 영어", "공부룩", "쌓아놓은 책", "음료", "지우개 모양", "지금 시간",
        "공부 메이트", "휴식", "공부 간식", "오늘의 명언", "최애 과목", "오늘의 포인트")

    var covid19=arrayOf("마스크", "덕분에 챌린지", "사회적 거리두기", "영상통화", "혼영", "손소독제", "손 씻기", "오늘의 체온"
        , "의료진(병원)", "방명록", "건강", "홈트", "환기", "열화상카메라", "자가진단", "확찐자", "팔꿈치 인사", "마스크 자르기", "방구석 여행", "레몬 챌린지")

    var food=arrayOf("과일", "최애 음식", "오늘 아침", "오늘 점심", "오늘 저녁", "오늘 간식", "편식", "달달", "단짠", "스트레스 해소", "요리", "집밥"
        , "중식", "일식", "겨울 필수", "여름 필수", "건강식", "보양식", "설날", "추석")

    var mission=arrayOf("브이하기", "스트레칭", "길거리 꽃", "엽사", "안부 메시지", "소중한 사람과 함께", "최근 받은 선물", "최근 산 물건", "추천템", "지금 눈 앞에 있는 것", "인싸 포즈", "홈 패션쇼"
        , "지금 옆에 있는 ?", "최애 옷", "나만의 비밀", "길냥이", "애착물건", "오늘 기분", "최근 과소비", "지금 실검1위")

    var me=arrayOf("생일날 먹은 것", "최고의 선물", "최고의 칭찬", "최고의 말", "편지", "과거의 오늘", "감사 일기", "오늘의 기분", "셀프 응원", "당당한 표정",
        "소중한 사람과 함께", "사소한 취미", "오늘의 소확행", "매력", "최애옷", "오늘의 TMI", "오늘의 지식", "내가 좋아하는 것", "오늘 잘 한 일", "친구에게 장점 묻기")


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_open.setOnClickListener{
            drawer_layout.openDrawer(GravityCompat.END)
        }

        btn_sky.setOnClickListener{
            val cate=Cate(sky)

//            for(i in cate.arg.indices){
//                Log.d("Cate : #*(#$&@$*$&(@*&&@$(@)@(#*)!(#)*#*)@#*#@", cate.arg[i])
//            }
            val intent=Intent(this, galleryActivity::class.java)   //다음 화면으로 이동하기 위한 인텐트 객체 생성
            startActivity(intent)
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
}