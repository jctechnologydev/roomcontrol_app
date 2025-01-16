package com.ruialves.dtt_assessment.util

import okhttp3.Interceptor
import okhttp3.Response
import pt.ipca.smartrooms.ServiceLocator

/**
 * AuthInterceptor.kt
 * Interceptor used to add additional information to requests
 * @author Rui Alves
 * @date 02-10-2022
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        //Add header to all calls
        val requestBuilder = original.newBuilder()

        val token = ServiceLocator.tokenSP.getToken()//"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJURUNITklDSUFOIiwiVXNlcklEIjoiNiIsImV4cCI6MTY3MzQwNjg5MywiaXNzIjoiSVBDQSBJU0kgSXNzdWVyIiwiYXVkIjoiQ29tdW5pZGFkZSBJUENBIn0.aFguOGtVnaxDv3dQGMbPx6cLtm7n_8zZXTeEOf76yZo"
        if(!token.isNullOrEmpty())
            requestBuilder.addHeader("Authorization", token)

        return chain.proceed(requestBuilder.build())
    }

}