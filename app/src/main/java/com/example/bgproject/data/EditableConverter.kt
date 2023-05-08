package com.example.bgproject.data

import android.text.Editable
import android.text.SpannableStringBuilder
import androidx.room.TypeConverter

class EditableConverter {
    @TypeConverter
    fun fromEditable(editable: Editable?): String? {
        return editable?.toString()
    }

    @TypeConverter
    fun toEditable(value: String?): Editable? {
        return value?.let { SpannableStringBuilder(it) }
    }
}

