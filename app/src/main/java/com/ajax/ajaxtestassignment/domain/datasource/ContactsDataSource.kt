package com.ajax.ajaxtestassignment.domain.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.ajax.ajaxtestassignment.api.contacts.ContactsService
import com.ajax.ajaxtestassignment.db.AppDatabase
import com.ajax.ajaxtestassignment.domain.mappers.toDbModel
import com.ajax.ajaxtestassignment.domain.mappers.toDomain
import com.ajax.ajaxtestassignment.domain.model.Contact

interface ContactsDataSource {

    val contactsPagingSource: PagingSource<Int, Contact>

    suspend fun loadContacts(page: Int, results: Int = 30): List<Contact>

    suspend fun loadContact(id: String): Contact?
    suspend fun saveContacts(contacts: List<Contact>, isRefresh: Boolean)
    suspend fun deleteAll()
    suspend fun deleteContact(id: Int)
}

internal class ContactsDataSourceImpl(
    private val api: ContactsService,
    //todo: inject dao maybe
    private val db: AppDatabase
) : ContactsDataSource {

    private val dao = db.userDao()

    override val contactsPagingSource: PagingSource<Int, Contact>
        get() = dao.getContacts()


    override suspend fun loadContact(id: String): Contact? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun loadContacts(page: Int, results: Int): List<Contact> {
        val url = "https://wm0.mobimate.com/content/worldmate/currencies/currency2008.dat"

        return api.getContacts(url)
//            .execute().body()?.string()
            .orEmpty().split("\n").map {
            Contact(id = it, firstName = it, lastName = it, email = it, picture = it)
        }
//        return api.getContacts(page, results).results.orEmpty().map {
//            it.toDomain()
//        }
    }

    override suspend fun saveContacts(contacts: List<Contact>, isRefresh: Boolean) {
        db.withTransaction {
            if (isRefresh) {
                dao.deleteAll()
            }

            dao.addAll(
                contacts.map { it.toDbModel() }
            )
        }
    }

    override suspend fun deleteContact(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}