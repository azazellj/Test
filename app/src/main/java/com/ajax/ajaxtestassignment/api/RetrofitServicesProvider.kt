package com.ajax.ajaxtestassignment.api

import com.ajax.ajaxtestassignment.api.contacts.ContactsService
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitServicesProvider {
    private val moshi: Moshi = Moshi.Builder()
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val contactsService: ContactsService
        get() = retrofit.create(ContactsService::class.java)
}