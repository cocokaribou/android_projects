package com.example.custom_logview.ui

import android.util.Log
import com.example.custom_logview.api.service
import com.example.custom_logview.model.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MainRepository {
    suspend fun requestMockData1(): Flow<Result<List<Data>>> {
        return flow {
            val data = service.mockData1()
            Log.e("youngin", "$data")
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.catch {
            emit(Result.failure(Throwable("mockData1")))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestFailingData(): Flow<Result<List<Data>>> {
        return flow {
            val data = service.failingData()
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.catch {
            emit(Result.failure(Throwable("fail!")))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestMockData2(): Flow<Result<List<Data>>> {
        return flow {
            val data = service.mockData2()
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.catch {
            emit(Result.failure(Throwable("mockData2")))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestMockData3(): Flow<Result<List<Data>>> {
        return flow {
            val data = service.mockData3()
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.catch {
            emit(Result.failure(Throwable("mockData2")))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestMockData4(): Flow<Result<List<Data>>> {
        return flow {
            val data = service.mockData4()
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.catch {
            emit(Result.failure(Throwable("mockData2")))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestMockData5(): Flow<Result<List<Data>>> {
        return flow {
            val data = service.mockData5()
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.catch {
            emit(Result.failure(Throwable("mockData2")))
        }.flowOn(Dispatchers.IO)
    }
}