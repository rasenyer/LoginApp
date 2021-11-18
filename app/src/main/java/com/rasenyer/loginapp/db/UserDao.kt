package com.rasenyer.loginapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rasenyer.loginapp.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user_table WHERE username LIKE :userName")
    suspend fun getByUsername(userName: String): User?

    @Query("SELECT * FROM user_table ORDER BY id DESC")
    fun getAll(): LiveData<List<User>>

    @Query("DELETE FROM user_table")
    suspend fun deleteAll(): Int

}