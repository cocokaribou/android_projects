package com.example.elandmall_kotlin.ui.main.tabs.eshop

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.model.EKidsResponse
import com.example.elandmall_kotlin.model.EshopResponse
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class EShopRepository {
    suspend fun requestEshopStream(): Flow<Result<EshopResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/eshop.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, EshopResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}