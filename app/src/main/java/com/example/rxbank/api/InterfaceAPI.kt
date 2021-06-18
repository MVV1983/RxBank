package com.example.rxbank.api

import com.example.rxbank.data.requests.LoginRequest
import com.example.rxbank.data.requests.NewLoanRequest
import com.example.rxbank.data.response.LoanConditions
import com.example.rxbank.data.response.LoanData
import com.example.rxbank.data.response.LoginResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface InterfaceAPI {
    @POST("registration")
    fun postRequest(@Body registrationData: LoginRequest): Single<LoginResponse>

    @POST("login")
    fun postLogin(@Body data: LoginRequest): Single<String>

    @POST("loans")
    fun postLoans(@Header("Authorization") auth: String, @Body data: NewLoanRequest): Single<LoanData>

    @GET("loans/conditions")
    fun getLoanConditions(@Header("Authorization") authorize: String): Single<LoanConditions>
}