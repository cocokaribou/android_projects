package com.example.shared_viewmodel.model

import com.example.shared_viewmodel.CommonConst.USE_LOCAL_MAIN_HOME
import com.example.shared_viewmodel.api.ApiService
import com.example.shared_viewmodel.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class MainHomeRepository {
    private val service = ApiService.getApiService()
    private val mainHomeResult = MutableSharedFlow<MainHomeResult>(replay = 1)
    suspend fun searchMainHomeStream() : Flow<MainHomeResult> {
        requestMainHomeRepo()
        return mainHomeResult
    }

    private suspend fun requestMainHomeRepo() {
        if (USE_LOCAL_MAIN_HOME) {
            val jsonString = getJsonFileToString("jsons/mainHome.json")
            val response = Gson().fromJson(jsonString, HomeResponse::class.java)
            try {
                mainHomeResult.emit(MainHomeResult.Success(response))
            } catch (e: Exception) {
                mainHomeResult.emit(MainHomeResult.Error(e))
            }
        } else {
            try {
                val response = service.mainHome()
                mainHomeResult.emit(MainHomeResult.Success(response))
            } catch (e: Exception) {
                mainHomeResult.emit(MainHomeResult.Error(e))
            }
        }
    }
}