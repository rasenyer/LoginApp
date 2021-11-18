package com.rasenyer.loginapp.repository

import com.rasenyer.loginapp.db.UserDao
import com.rasenyer.loginapp.model.User

class UserRepository(private val dao: UserDao) {

    suspend fun insert(user: User) {
        return dao.insert(user)
    }

    suspend fun getByUsername(userName: String): User?{
        return dao.getByUsername(userName)
    }

    val getAll = dao.getAll()

}