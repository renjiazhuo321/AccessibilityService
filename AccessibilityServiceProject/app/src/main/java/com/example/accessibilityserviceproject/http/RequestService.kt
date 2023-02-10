package com.example.accessibilityserviceproject.http

import com.example.accessibilityserviceproject.http.NewBaseResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface RequestService {
    /**
     * 空间列表
     *
     * 已调完
     */
    @JvmSuppressWildcards
    @POST("api/b2c/space/list/get")
    suspend fun getSpace(
        @HeaderMap map: Map<String, Any>,
        @Body body: RequestBody
    ): Response<NewBaseResponse<String>>

}