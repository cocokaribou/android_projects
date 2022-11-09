package com.example.elandmall_kotlin.ui.intro

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.ui.BaseRepository
import com.example.elandmall_kotlin.common.AppConfig.USE_LOCAL_GNB
import com.example.elandmall_kotlin.common.AppConfig.USE_LOCAL_HOME
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.model.MainGnbResponse
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class IntroRepository : BaseRepository() {
    suspend fun requestGnbStream(): Flow<Result<MainGnbResponse>> {
        return flow {
            val data = if (USE_LOCAL_GNB) {
                val jsonString = getJsonFileToString("json/mainGnb.json", BaseApplication.context)
                Gson().fromJson(jsonString, MainGnbResponse::class.java)
            } else {
                service.getNewGnbMenu()
            }
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestHomeStream(): Flow<Result<HomeResponse?>> {
        return flow {
            val data = if (USE_LOCAL_HOME) {
                val jsonString = getJsonFileToString("json/home.json", BaseApplication.context)
                Gson().fromJson(jsonString, HomeResponse::class.java)
            } else {
                service.getHomeData()
            }
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}