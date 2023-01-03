package com.example.elandmall_kotlin.ui.main.tabs.plan

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.model.LuckyDealResponse
import com.example.elandmall_kotlin.model.PlanResponse
import com.example.elandmall_kotlin.util.getJsonFileToString
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class PlanRepository {
    suspend fun requestPlanStream(): Flow<Result<PlanResponse?>> {
        return flow {
            val jsonString = getJsonFileToString("json/plan_0_page1.json", BaseApplication.context)
            val data = Gson().fromJson(jsonString, PlanResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun requestPlanTabStream(dispCtgNo: String, index: Int): Flow<Result<PlanResponse?>> {
        return flow {
            val jsonString = when (index) {
                0 -> getJsonFileToString("json/plan_0_page1.json", BaseApplication.context)
                1 -> getJsonFileToString("json/plan_1.json", BaseApplication.context)
                2 -> getJsonFileToString("json/plan_2.json", BaseApplication.context)
                3 -> getJsonFileToString("json/plan_3.json", BaseApplication.context)
                4 -> getJsonFileToString("json/plan_4.json", BaseApplication.context)
                else -> getJsonFileToString("json/plan_5.json", BaseApplication.context)
            }

            val data = Gson().fromJson(jsonString, PlanResponse::class.java)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}