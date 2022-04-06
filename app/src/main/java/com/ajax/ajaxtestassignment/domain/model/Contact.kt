package com.ajax.ajaxtestassignment.domain.model

data class Contact(
    val id: String,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val picture: String?,

    ) {
    val fullName: String
        get() = "$firstName $lastName".trim()
}