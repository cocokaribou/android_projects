package com.example.elandmall_kotlin.repository

import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.model.MainGnbResponse
import kotlinx.coroutines.flow.Flow

interface MajorRepository {
    suspend fun requestHomeStream(): Flow<Result<HomeResponse?>>
}