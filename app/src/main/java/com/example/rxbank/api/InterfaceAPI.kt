package com.example.rxbank.api

import com.example.rxbank.data.requests.LoginRequest
import com.example.rxbank.data.requests.NewLoanRequest
import com.example.rxbank.data.response.LoanConditions
import com.example.rxbank.data.response.LoanData
import com.example.rxbank.data.response.LoansItem
import com.example.rxbank.data.response.LoginResponse
import io.reactivex.Single
import retrofit2.http.*

interface InterfaceAPI {
    @POST("registration")
    fun postRequest(@Body registrationData: LoginRequest): Single<LoginResponse>

    @POST("login")
    fun postLogin(@Body data: LoginRequest): Single<String>

    @POST("loans")
    fun postLoans(@Header("Authorization") auth: String, @Body data: NewLoanRequest): Single<LoanData>

    @GET("loans/conditions")
    fun getLoanConditions(@Header("Authorization") authorize: String): Single<LoanConditions>

    @GET("loans/all")
    fun getLoansList(@Header("Authorization") auth: String): Single<List<LoansItem>>

    @GET("loans/{id}")
    fun getLoanData(@Path("id") id: Int, @Header("Authorization") auth: String): Single<LoanData>
}