package com.example.youngin.activity
//
//import android.content.Intent
//import android.graphics.drawable.Drawable
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import androidx.appcompat.app.AppCompatActivity
//import com.example.youngin.R
//
//class SplashActivity : AppCompatActivity() {
//    val handler = Handler(Looper.getMainLooper()) //Handler() <- deprecated
//
//    /**
//     * splash를 fragment가 아닌 activity로 적
//     */
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val styleId = resources.getIdentifier("SplashTheme", "style", packageName)
//        val drawableFromPath = Drawable.createFromPa용th("some image url")
//
//        handler.postDelayed(Runnable() {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 2000)
//    }
//
//    override fun finish() {
//        super.finish()
//        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//    }
//
//}