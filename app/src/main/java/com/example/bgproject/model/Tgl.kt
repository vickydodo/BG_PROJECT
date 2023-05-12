package com.example.bgproject.model

import android.net.Uri
import android.text.Editable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tgl_table")
data class Tgl(

    @PrimaryKey
    var tglId: String,
    var fullName: String,
    var number: String,
    var sex: Editable,
    var dob: Editable,
    var bvn: String,
    var nin: String,
    var state: Editable,
    var lga: String,
    var hub: String,
    var govType: Editable,
    var govId: String,
    var govImage: String,
    var officerId: String =""

)
