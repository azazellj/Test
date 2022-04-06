package com.ajax.ajaxtestassignment.db.contacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact")
data class DbContact(
    @PrimaryKey val id: String,
    @ColumnInfo val firstName: String?,
    @ColumnInfo val lastName: String?,
    @ColumnInfo val email: String?,
    @ColumnInfo val photo: String?,
)
