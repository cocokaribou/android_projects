package com.example.elandmall_kotlin.ui.main.tabs.home

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.common.AppConfig
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.repository.MajorRepository
import com.example.elandmall_kotlin.ui.BaseRepository
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class HomeRepository: BaseRepository(), MajorRepository {
    override suspend fun requestHomeStream(): Flow<Result<HomeResponse?>> {
        return flow {
            val data = if (AppConfig.USE_LOCAL_HOME) {
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