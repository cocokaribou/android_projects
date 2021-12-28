#### response body 타입이 text/plain일 때
```kotlin
callBack.enqueue(object : Callback<ResponseBody> {
    override fun onResponse(call: Call<ResponseBody>, reponse: Response<ResponseBody>) {
        if (reponse.isSuccessful) {
            val body : String? = reponse.body()?.string()

            // or
            // val body : String? = URLDecoder.decode(response.body()?.string(),"utf-8")
        }
    }
})
```

#### response body 타입이 json일 때
```kotlin
callBack.enqueue(object : Callback<ResponseBody> {
    override fun onResponse(call: Call<ResponseBody>, reponse: Response<ResponseBody>) {
        if (reponse.isSuccessful) {

            resultObject = JSONObject(response.body()!!.string())

            // json에서 추출하고 싶은 값
            if (JSONObject(response.body()!!.string).has("store_list")) {
            	storeList = JSONObject(response.body()!!.string()).get("store_list") as JSONArray
            }

            // gson library, POJO로 전환
            resultObject2 = gson.fromJson(response.body()!!.string(), MyData::class.java)
        }
    }
})
```