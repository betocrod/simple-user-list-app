package com.example.simpleuserlistapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun add(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user_entity")
    fun getAll(): Flow<List<User>>
}