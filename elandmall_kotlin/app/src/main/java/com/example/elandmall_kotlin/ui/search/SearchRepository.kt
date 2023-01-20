package com.example.elandmall_kotlin.ui.search

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.model.*
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class SearchRepository {
    suspend fun requestPopularStream(): Flow<Result<SearchPopularResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/search_popular.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, SearchPopularResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestBrandStream(): Flow<Result<SearchBrandResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/search_brand.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, SearchBrandResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestPlanShopStream(): Flow<Result<SearchPlanShopResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/search_planshop.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, SearchPlanShopResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestBrandKeywordStream(): Flow<Result<SearchBrandKeyword?>> {
        return flow {
            val jsonString = getJsonFileToString("json/search_brand_keyword.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, SearchBrandKeyword::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestBrandKeywordListStream(): Flow<Result<SearchBrandKeywordList?>> {
        return flow {
            val jsonString = getJsonFileToString("json/search_brand_keyword_list.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, SearchBrandKeywordList::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}