package com.example.web_view2.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.web_view2.R

class SplashActivity : AppCompatActivity() {
    val handler = Handler(Looper.getMainLooper()) //Handler() <- deprecated

    /**
     * splash activity에 theme 적용해서 splash 올리기
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //xml 동적으로 변경.. 일단 실험해보기
        val styleId = resources.getIdentifier("SplashTheme", "style", packageName)
        val drawableFromPath = Drawable.createFromPath("some image url")

        //안되나봄^^ 그냥 fragment로 가자

        handler.postDelayed(Runnable() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

}