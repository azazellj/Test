package com.ajax.ajaxtestassignment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.ajax.ajaxtestassignment.db.contacts.ContactsDao
import com.ajax.ajaxtestassignment.db.contacts.DbContact
import com.ajax.ajaxtestassignment.domain.mappers.toDbModel
import com.ajax.ajaxtestassignment.domain.mappers.toDomain
import com.ajax.ajaxtestassignment.domain.model.Contact

@Database(entities = [DbContact::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): ContactsDao
}


class Converters {
    @TypeConverter
    fun fromDomain(contact: Contact?): DbContact? {
        return contact?.toDbModel()
    }

    @TypeConverter
    fun toDomain(contact: DbContact?): Contact? {
        return contact?.toDomain()
    }
}