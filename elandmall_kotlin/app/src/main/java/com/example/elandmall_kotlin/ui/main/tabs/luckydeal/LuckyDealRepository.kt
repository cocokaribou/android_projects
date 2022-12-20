package com.example.elandmall_kotlin.ui.main.tabs.luckydeal

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.model.LuckyDealResponse
import com.example.elandmall_kotlin.model.LuckyDealTabResponse
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class LuckyDealRepository {

    suspend fun requestLuckyDealStream(): Flow<Result<LuckyDealResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/luckydeal.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, LuckyDealResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestLuckyDealTabStream(conrSetNo: String, conrSetCmpsNo: String): Flow<Result<LuckyDealTabResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/luckydeal_tab.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, LuckyDealTabResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}