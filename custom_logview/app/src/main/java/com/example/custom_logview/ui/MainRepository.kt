package com.example.custom_logview.ui

import com.example.custom_logview.api.ApiService
import com.example.custom_logview.model.MainGnbResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class MainRepository {
    suspend fun requestGnbStream(): Flow<Result<MainGnbResponse>> {
        return flow {
            val data = ApiService.service().getNewGnbMenu()
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}