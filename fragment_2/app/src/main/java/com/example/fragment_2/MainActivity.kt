package com.example.fragment_2

import com.pionnet.overpass.module.LogHelper
import com.pionnet.overpass.module.PaymentModule
import com.pionnet.overpass.module.NetworkManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        LogHelper.initTag("youngin")
        super.onCreate(savedInstanceState)

        // setReorderingAllowed : 트랜잭션과 관련된 프래그먼트의 상태변경 최적화

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


            var comment = ""

            NetworkManager.checkNetworkAvailable(this, object: NetworkManager.OnNetworkListener{
                override fun networkAvailable() {
                    comment = "network available"
                }

                override fun finishApp() {
                    comment = "network not available"
                }

            })
            val bundle = bundleOf("some_string" to comment)
            supportFragmentManager.commit{
                setReorderingAllowed(true)
                add<RootFragment>(R.id.fragment_container_view, args=bundle)
            }
        }


    }
}