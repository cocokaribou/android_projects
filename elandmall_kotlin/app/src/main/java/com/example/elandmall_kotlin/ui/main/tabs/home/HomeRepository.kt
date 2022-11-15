package com.example.elandmall_kotlin.ui.main.tabs.home

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.repository.MajorRepository
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class HomeRepository: MajorRepository {
    override suspend fun requestHomeStream(): Flow<Result<HomeResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/home.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, HomeResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

}