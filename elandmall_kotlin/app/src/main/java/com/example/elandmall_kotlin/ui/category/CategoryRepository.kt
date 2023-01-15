package com.example.elandmall_kotlin.ui.category

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.model.BestResponse
import com.example.elandmall_kotlin.model.CategoryResponse
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class CategoryRepository {
    suspend fun requestCategoryStream(): Flow<Result<CategoryResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/leftMenu.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, CategoryResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}