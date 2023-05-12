package com.example.bgproject.repository

import androidx.lifecycle.LiveData
import com.example.bgproject.data.UserDao
import com.example.bgproject.model.Tgl
import com.example.bgproject.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllTglData = userDao.readAllTglData()

//    val getTglByUser = userDao.getTglByUser()



    suspend fun registerUser(user: User){
        userDao.insert(user)
    }

    suspend fun registerTgl(tgl: Tgl){
        userDao.insertTgl(tgl)
    }

    suspend fun login(email: String, password: String):User?{
        return userDao.getUserByEmailAndPassword(email, password)
    }

    suspend fun alreadyUser(email: String):User?{
        return userDao.getUserByEmail(email)
    }

    fun getTglByUser(officerId: String):LiveData<List<Tgl>>{
        return userDao.getTglByUser(officerId)
    }

}