package com.example.simpleuserlistapp.database

import kotlinx.coroutines.flow.Flow

class UsersRepository(private val userDao: UserDao) {


    suspend fun add(user: User) {
        userDao.add(user)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    suspend fun getAll(): Flow<List<User>> {
        return userDao.getAll()
    }

    companion object {

        private var INSTANCE: UsersRepository? = null

        fun getInstance(userDao: UserDao): UsersRepository {
            if (INSTANCE == null) {
                INSTANCE = UsersRepository(userDao)
            }
            return INSTANCE!!
        }
    }
}