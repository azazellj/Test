package com.ajax.ajaxtestassignment.domain.interactors

import androidx.paging.PagingSource
import com.ajax.ajaxtestassignment.domain.datasource.ContactsDataSource
import com.ajax.ajaxtestassignment.domain.model.Contact
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ContactsInteractor {
    val contactsListFlow: PagingSource<Int, Contact>

    suspend fun loadContacts(page: Int, results: Int): List<Contact>

    suspend fun loadContact(id: String): Contact?
    suspend fun saveContacts(contacts: List<Contact>, isRefresh: Boolean)
    suspend fun deleteAll()
}

internal class ContactsInteractorImpl(
    private val contactsDataSource: ContactsDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO, //todo: inject
) : ContactsInteractor {

    override val contactsListFlow: PagingSource<Int, Contact>
        get() = contactsDataSource.contactsPagingSource

    override suspend fun loadContact(id: String): Contact? {
        return withContext(ioDispatcher) {
            contactsDataSource.loadContact(id)
        }
    }

    override suspend fun loadContacts(page: Int, results: Int): List<Contact> {
        return withContext(ioDispatcher) {
            contactsDataSource.loadContacts(page, results)
        }
    }

    override suspend fun saveContacts(contacts: List<Contact>, isRefresh: Boolean) {
        withContext(ioDispatcher) {
            contactsDataSource.saveContacts(contacts, isRefresh)
        }
    }

    override suspend fun deleteAll() {
        withContext(ioDispatcher) {
            contactsDataSource.deleteAll()
        }
    }
}