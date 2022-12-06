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

    var randCounter = 0
    suspend fun requestStorePickStream(): Flow<Result<StorePickResponse?>> {
        return flow {
            // for refreshing effect
            if (randCounter >= 3) {
                randCounter = 0
            } else {
                randCounter++
            }

            val jsonString = when (randCounter) {
                0 -> getJsonFileToString("json/storePick.json", BaseApplication.context)
                1 -> getJsonFileToString("json/storePick2.json", BaseApplication.context)
                2 -> getJsonFileToString("json/storePick3.json", BaseApplication.context)
                else -> getJsonFileToString("json/storePick4.json", BaseApplication.context)
            }
            val data = Gson().fromJson(jsonString, StorePickResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}