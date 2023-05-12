package com.example.bgproject.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.bgproject.data.UserDao
import com.example.bgproject.data.UserDatabase
import com.example.bgproject.model.Tgl
import com.example.bgproject.model.User
import com.example.bgproject.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID


class UserViewModel(application: Application) : AndroidViewModel(application) {

    val readAllTgl : LiveData<List<Tgl>>
    var getTglByUser : LiveData<List<Tgl>>

    private val repository: UserRepository
    var user: User? = null
    var tgl: Tgl? = null

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

    }

    suspend fun registerUser() {
            user?.let { repository.registerUser(it) }
    }

    suspend fun registerTgl(){
        tgl?.let{repository.registerTgl(it)}
    }

    suspend fun login(email: String, password: String) {
            user = repository.login(email, password)
    }

    suspend fun alreadyUser(email: String) {
            user = repository.alreadyUser(email)
    }

    fun insertOfficerIdentification(tglIdentification: Tgl, context: Context, imageUri: Uri) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // First, save the image to internal storage
                val imageFile = File(context.filesDir, UUID.randomUUID().toString())
                val inputStream = context.contentResolver.openInputStream(imageUri)
                inputStream.use { input ->
                    imageFile.outputStream().use { output ->
                        input?.copyTo(output)
                    }
                }

                // Then, update the officer identification object with the image path
                tglIdentification.govImage = imageFile.absolutePath
                repository.registerTgl(tglIdentification)
            }
        }
    }


}