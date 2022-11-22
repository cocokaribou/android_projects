package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.model.StorePickResponse
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class StoreShopRepository {
    suspend fun requestStoreShopStream(): Flow<Result<StoreShopResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/storeShop.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, StoreShopResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestStorePickStream(): Flow<Result<StorePickResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/storePick.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, StorePickResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}