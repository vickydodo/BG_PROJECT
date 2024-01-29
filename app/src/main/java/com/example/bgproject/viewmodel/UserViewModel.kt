package com.example.bgproject.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.bgproject.data.UserDatabase
import com.example.bgproject.fragments.question.Constants
import com.example.bgproject.fragments.question.Question
import com.example.bgproject.model.Result
import com.example.bgproject.model.Tgl
import com.example.bgproject.model.User
import com.example.bgproject.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID


class UserViewModel(application: Application) : AndroidViewModel(application) {

    val readAllTgl: LiveData<List<Tgl>>
    var getTglByUser: LiveData<List<Tgl>>
    var getResultByTgl : LiveData<List<Result>>
    val scheduleTgl : LiveData<List<Tgl>>
    val resultList: MutableList<Result> = mutableListOf()



    private val repository: UserRepository
    val userSelections = mutableMapOf<Int, Int>()
    var questionList: ArrayList<Question> = Constants.getQuestions()
    var user: User? = null
    var tgl: Tgl? = null
    var tglId : String = ""


    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
        readAllTgl = repository.readAllTglData
        val sharedPreferences =
            application.applicationContext.getSharedPreferences(
                "my_preferences",
                Context.MODE_PRIVATE
            )
        val officerId = sharedPreferences.getString("OFFICER_ID", "").toString()
        getTglByUser = repository.getTglByUser(officerId)

        val sharedPreferences2 =
            application.applicationContext.getSharedPreferences(
                "my_preferences",
                Context.MODE_PRIVATE
            )
        tglId = sharedPreferences2.getString("TGL_ID", "").toString()
        getResultByTgl = repository.getResultByTgl(tglId)

        val sharedPreferences1 =
            application.applicationContext.getSharedPreferences(
                "my_preferences",
                Context.MODE_PRIVATE
            )
        tglId = sharedPreferences1.getString("TGL_ID", "").toString()

        scheduleTgl = repository.scheduleTgl()

        questionList = Constants.getQuestions()
        resultList.addAll(List(questionList.size) { Result() })

        
    }

    suspend fun registerUser() {
        user?.let { repository.registerUser(it) }
    }

    suspend fun registerTgl(tgl: Tgl?, context: Context, imageUri: Uri) {
        val imageFile = File(context.filesDir, UUID.randomUUID().toString())
        val inputStream = context.contentResolver.openInputStream(imageUri)
        inputStream.use { input ->
            imageFile.outputStream().use { output ->
                input?.copyTo(output)
            }
        }
        // Then, update the officer identification object with the image path
        tgl?.govImage = imageFile.absolutePath
        tgl?.let { repository.registerTgl(it) }
    }

    suspend fun updateTgl() {
        tgl?.let { repository.updateTgl(it) }
    }

    fun updateTgl(tgl: Tgl) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.registerTgl(tgl)
        }
    }

    fun registerResult() {
        viewModelScope.launch(Dispatchers.IO) {
            for (result in resultList)
                repository.insertResult(result)
        }
    }

    suspend fun login(email: String, password: String) {
        user = repository.login(email, password)
    }

    suspend fun alreadyUser(email: String) {
        user = repository.alreadyUser(email)
    }


    fun updateUserSelection(questionPosition: Int, selectedOptionPosition: Int) {
        userSelections[questionPosition] = selectedOptionPosition
        val question = questionList[questionPosition]


        val result = Result(
            tglId = tglId,
            questionId = question.id,
            question = question.question,
            selectedOption = question.options[question.selectedOptionPosition],
            correctAnswer = question.options[question.correctAnswer]

        )
        resultList[questionPosition] = result
    }

    fun getSelectedOptions(): Map<Int, Int> {
        return userSelections
    }

    fun getSelectedOption(questionPosition: Int): Int? {
        return userSelections[questionPosition]
    }

    fun evaluateAnswers(questionList: List<Question>): Int {
        var score = 0
        for (i in questionList.indices) {
            val question = questionList[i]
            val userSelection = userSelections[i]


            if (userSelection != null) {
                question.selectedOptionPosition = userSelection
                question.isAnswered = true

                if (userSelection == question.correctAnswer) {
                    ++score
                }
            }
        }
        return score
    }

    fun getQuestions(): ArrayList<Question> {
        return questionList
    }




}