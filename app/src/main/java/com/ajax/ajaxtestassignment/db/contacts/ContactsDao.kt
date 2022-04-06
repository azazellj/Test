package com.ajax.ajaxtestassignment.db.contacts

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ajax.ajaxtestassignment.domain.model.Contact

@Dao
interface ContactsDao {
    @Query("SELECT * FROM Contact")
    fun getContacts(): PagingSource<Int, Contact>

    @Update
    suspend fun update(contact: DbContact)

    @Insert
    suspend fun addAll(contact: List<DbContact>)

    @Query("DELETE FROM Contact WHERE id = (:contactId)")
    suspend fun deleteById(contactId: Int)

    @Query("SELECT * FROM Contact WHERE id = (:contactId)")
    suspend fun getById(contactId: String): DbContact?

    @Query("DELETE FROM Contact")
    suspend fun deleteAll()
}