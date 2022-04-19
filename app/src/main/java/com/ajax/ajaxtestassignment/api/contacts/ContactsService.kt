package com.ajax.ajaxtestassignment.api.contacts

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ContactsService {
    @GET
    suspend fun getContacts(
        @Url url: String,
//        @Query("page") page: Int,
//        @Query("results") limit: Int = 30,
    ): String
//    ): ApiContactResponse
}