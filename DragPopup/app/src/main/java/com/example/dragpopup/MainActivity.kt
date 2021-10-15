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

    lateinit var popUpFrag: PopupLayout
    lateinit var promoFrag: PromoFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread {
            setPopupUi(getDataList())
        }.start()
    }

    fun setPopupUi(dataList: MutableList<Promotion>) {
        popUpFrag = PopupLayout(dataList)
        promoFrag = PromoFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.frameFirst, popUpFrag)
            .commit()
        binding.dragview.setDragListener(object : DragView.DragListener {
            override fun onChangeState(state: DragView.State) {
                when (state) {
                    DragView.State.MAX -> {
                        Log.e("max", "when is this")
                        binding.background.visibility = View.GONE
                        supportFragmentManager.beginTransaction()
                            .add(R.id.container, promoFrag)
                            .commit()
                    }
                    DragView.State.CLOSE -> {
                        binding.dragview.close()
                    }
                    DragView.State.MIN -> {
                        Log.e("min", "when is this")
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
                imgUrl = "https:"+content.select("img").attr("src"),
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