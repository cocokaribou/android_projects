package com.youngin.lunch.ui

import com.youngin.lunch.BaseRepository
import com.youngin.lunch.model.Category
import com.youngin.lunch.model.StoreDataList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen

class MainRepository : BaseRepository() {
    fun requestStoreListStream(): Flow<Result<StoreDataList>> {

        return flow {
            val data = service.getStoreList(cateNo = Category.selectedCate.no)
            emit(Result.success(data))
        }.retryWhen { cause, attempt ->
            return@retryWhen attempt < 2 && cause is java.lang.Exception
        }.flowOn(Dispatchers.IO)
    }
}