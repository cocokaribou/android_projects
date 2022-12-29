package com.example.elandmall_kotlin.ui.main.tabs.best

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.model.BestResponse
import com.example.elandmall_kotlin.model.BestTabResponse
import com.example.elandmall_kotlin.model.LuckyDealResponse
import com.example.elandmall_kotlin.model.LuckyDealTabResponse
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class BestRepository {
    suspend fun requestBestStream(): Flow<Result<BestResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/best.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, BestResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    var randCounter = 0
    suspend fun requestBestTabStream(dispCtgNo: String, pageIdx: Int): Flow<Result<BestTabResponse?>> {
        return flow {
            val jsonString = when (randCounter) {
                0 -> getJsonFileToString("json/best_tab.json", BaseApplication.context)
                else -> getJsonFileToString("json/best_tab2.json", BaseApplication.context)
            }

            // for refreshing effect
            if (randCounter >= 1) {
                randCounter = 0
            } else {
                randCounter++
            }
            val data = Gson().fromJson(jsonString, BestTabResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}