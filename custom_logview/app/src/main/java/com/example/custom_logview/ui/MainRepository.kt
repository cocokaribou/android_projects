package com.example.custom_logview.ui

import android.util.Log
import com.example.custom_logview.api.service
import com.example.custom_logview.model.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MainRepository {
    suspend fun requestGnbData(): Flow<Result<BaseResponse>> {
        return flow {
            val data = service.getNewGnbMenu()
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.catch {
            emit(Result.failure(Throwable("gnb")))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestFooterData(): Flow<Result<BaseResponse>> {
        return flow {
            val data = service.getFooterData()
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.catch {
            emit(Result.failure(Throwable("footer")))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestPlanShopList(): Flow<Result<BaseResponse>> {
        return flow {
            val data = service.getPlanShopList()
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.catch {
            emit(Result.failure(Throwable("shop list")))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestPlanShopListMore(page: String = "2"): Flow<Result<BaseResponse>> {
        return flow {
            val data = service.getPlanShopListMore(page)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.catch {
            emit(Result.failure(Throwable("shop list page=$page")))
        }.flowOn(Dispatchers.IO)
    }
}