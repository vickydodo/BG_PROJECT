package com.example.bgproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("result_table")
data class Result(
    @PrimaryKey
    var tglId : String ="",
//    var questionNumber : Int = 0,
    var questionId: Int = 0,
    var question: String ="",
    var selectedOption: String="" ,
    var correctAnswer: String="" ,


)
