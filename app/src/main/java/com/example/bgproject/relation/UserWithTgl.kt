//package com.example.bgproject.relation
//
//import androidx.room.Embedded
//import androidx.room.Relation
//import com.example.bgproject.model.Tgl
//import com.example.bgproject.model.User
//
//data class UserWithTgl(
//    @Embedded val user: User,
//    @Relation(
//        parentColumn = "userId",
//        entityColumn = "userId"
//    )
//    val tgls: List<Tgl>
//)
