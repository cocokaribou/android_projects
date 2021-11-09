package com.example.fragment_2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.fragment_2.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1.
        // fragment add 코드상 하지 않고 xml 상 하기
        // FragmentContainerView name 어트리뷰트에 frag 클래스 추가
        // fragment 에서 onInflate() 호출됨
//        setContentView(R.layout.activity_main)


        // 2.
        // savedInstanceState 검사, 한번만 초기화하기 위해
//        if(savedInstanceState == null){
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                add<ExampleFragment>(R.id.fragment_container_view)
//            }
//        }

        // 3.
        // fragment 초기화할때 데이터 넘겨줄 수 있음
        if(savedInstanceState == null){
            val bundle = bundleOf("some_string" to "frag data init!")
            supportFragmentManager.commit{
                setReorderingAllowed(true)
                add<ExampleFragment>(R.id.fragment_container_view, args=bundle)
            }
        }



    }
}