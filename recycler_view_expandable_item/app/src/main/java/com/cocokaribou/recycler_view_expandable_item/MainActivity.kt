package com.cocokaribou.recycler_view_expandable_item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getList()
    }

    private fun getList(){
        val myApi = MyApi.getApiService()
        // 패션의류
        val callBack: Call<ResponseBody> = myApi.getBestProducts("1703314378")
        callBack.enqueue(object:Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("onFailure!", "통신실패")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("onResponse", "통신성공")
                val jsonString = response.body()?.string()
                val jsonObj = JSONObject(jsonString)
                val goodsList = jsonObj.getJSONObject("data").getJSONObject("goods_info").getJSONArray("goods_list")
                for(i in 0 .. goodsList.length()-1){
                    Log.e("굿즈 ${i+1}", "${goodsList[i]}")
                }
                Log.e("굿즈", "${goodsList[0]}")
            }

        })
    }
}