package com.ajax.ajaxtestassignment.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.ajax.ajaxtestassignment.api.RetrofitServicesProvider
import com.ajax.ajaxtestassignment.api.contacts.ContactsService
import com.ajax.ajaxtestassignment.db.AppDatabase
import com.ajax.ajaxtestassignment.domain.datasource.ContactsDataSource
import com.ajax.ajaxtestassignment.domain.datasource.ContactsDataSourceImpl
import com.ajax.ajaxtestassignment.domain.interactors.ContactsInteractor
import com.ajax.ajaxtestassignment.domain.interactors.ContactsInteractorImpl
import com.ajax.ajaxtestassignment.ui.contactslist.ContactsListViewModel
import com.ajax.ajaxtestassignment.ui.details.DetailsViewModel

object GlobalFactory : ViewModelProvider.Factory {

    val service: ContactsService by lazy {
        RetrofitServicesProvider().contactsService
    }

    lateinit var db: AppDatabase


    val contactsDataSource: ContactsDataSource by lazy {
        ContactsDataSourceImpl(
            api = service,
            db = db
        )
    }

    val contactsInteractor: ContactsInteractor by lazy {
        ContactsInteractorImpl(
            contactsDataSource = contactsDataSource
        )
    }

    fun init(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).build()
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            ContactsListViewModel::class.java -> ContactsListViewModel(
                contactsInteractor = contactsInteractor,
            )
            DetailsViewModel::class.java -> DetailsViewModel(
                contactsInteractor = contactsInteractor,
            )
            else -> throw IllegalArgumentException("Cannot create factory for ${modelClass.simpleName}")
        } as T
    }
}
