package com.example.bgproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("users_table")
data class User(
    @PrimaryKey
    var officerId: String,
    var fullName: String,
    var email: String,
    var password: String
)

