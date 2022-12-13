package com.example.elandmall_kotlin.ui.main.tabs.ekids

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.model.EKidsResponse
import com.example.elandmall_kotlin.model.PlanDetailResponse
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class EKidsRepository {
    suspend fun requestEKidsStream(): Flow<Result<EKidsResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/ekids.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, EKidsResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}