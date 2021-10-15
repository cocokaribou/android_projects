package com.example.dragpopup

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dragpopup.data.Promotion
import com.example.dragpopup.databinding.ActivityMainBinding
import com.example.dragpopup.webview.MyWebViewClient
import com.tuanhav95.drag.DragView
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MainActivity : AppCompatActivity(), MyWebViewClient.WebViewClientListener {
    lateinit var binding: ActivityMainBinding
    var list = mutableListOf<Promotion>()

    lateinit var popUpFrag: PopupLayout
    lateinit var promoFrag: PromoFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // data를 어디서 불러오면 좋을까..
        Thread {
            setPopupUi(getDataList())
        }.start()
    }

    fun setPopupUi(dataList: MutableList<Promotion>) {
        popUpFrag = PopupLayout(dataList)

//        val promoUrl:String = if (dataList[popUpFrag.getChildIndex()].promoUrl.isEmpty()) {
//            "https://m-kimsclub.elandmall.com/event/initEventDtl.action?event_no=E210912890"
//        } else {
//            dataList[popUpFrag.getChildIndex()].promoUrl
//        }
//        promoFrag = PromoFragment(promoUrl, binding.dragview)

        supportFragmentManager.beginTransaction()
            .add(R.id.frameFirst, popUpFrag)
            .commit()
        binding.dragview.setDragListener(object : DragView.DragListener {
            override fun onChangeState(state: DragView.State) {
                when (state) {
                    DragView.State.MAX -> {
                        binding.background.visibility = View.GONE
                        supportFragmentManager.beginTransaction()
                            .add(R.id.container, promoFrag)
                            .commit()
                    }
                    DragView.State.CLOSE -> {
                        // 이걸 못 읽는데..?
                        binding.dragview.close()
                    }
                    DragView.State.MIN -> {
                    }
                }
                super.onChangeState(state)
            }
        })

    }

    // jsoup 크롤링
    // TODO 문서 구조를 미리 알고 하드코딩으로 박아넣는 것이 최선인가...???
    fun getDataList(): MutableList<Promotion> {
        val promotionList = mutableListOf<Promotion>()

        val doc: Document =
            Jsoup.connect("https://m-kimsclub.elandmall.com/main/initMain.action").get()
        val listContents = doc.body().select("body").select(".wrapper").select(".container")
            .select(".layer_screen").select(".layer_box").select(".cont").select(".lst")
            .select("ul").select("li")

        listContents.forEach { content ->
            val promotion = Promotion(
                imgUrl = "https:" + content.select("img").attr("src"),
                promoUrl = content.select("a").attr("onclick").split("url:'")[1].split("'")[0]
            )
            promotionList.add(promotion)
        }
        return promotionList
    }


    override fun onPageStarted(url: String) {
        Log.e(javaClass.simpleName, "onPageStarted")
    }

    override fun onPageFinshed(url: String) {
        TODO("Not yet implemented")
    }


}