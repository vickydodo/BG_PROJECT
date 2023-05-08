package com.example.bgproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.bgproject.data.UserDatabase
import com.example.bgproject.model.Tgl
import com.example.bgproject.model.User
import com.example.bgproject.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(application: Application) : AndroidViewModel(application) {

    val readAllTgl : LiveData<List<Tgl>>

    private val repository: UserRepository
    var user: User? = null
    var tgl: Tgl? = null

    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
        readAllTgl = repository.readAllTglData
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

//    suspend fun getTglByUser(officerId: String){
//        repository.getTglByUser(officerId)
//    }


}