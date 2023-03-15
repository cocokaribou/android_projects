package com.example.elandmall_kotlin.ui.goods

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.GoodsResponse
import com.example.elandmall_kotlin.model.LeftMenuResponse
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class GoodsRepository {
    suspend fun requestGoodsStream(): Flow<Result<GoodsResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/goods_detail.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, GoodsResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}