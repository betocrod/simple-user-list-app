package com.example.simpleuserlistapp

class UsersRepository {

    private val users = mutableListOf<User>()

    fun add(user: User) {
        users.add(user)
    }

    fun delete(user: User) {
        users.remove(user)
    }

    fun getAll(): List<User> {
        return users
    }

    companion object {

        private var INSTANCE: UsersRepository? = null

        fun getInstance(): UsersRepository {
            if (INSTANCE == null) {
                INSTANCE = UsersRepository()
            }
            return INSTANCE!!
        }
    }
}