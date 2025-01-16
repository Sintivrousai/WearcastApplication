package com.example.wearcast.data.User

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun validateLogin(username: String, password: String): User? {
        return userDao.validateLogin(username, password)
    }

    // Get the full User object by username
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }
    suspend fun isUsernameTaken(username: String): Boolean {
        return userDao.isUsernameTaken(username)
    }

    suspend fun getUserIdByUsername(username: String): Int? {
        return userDao.getUserIdByUsername(username)
    }

    // Method to update the username
    suspend fun updateUsername(userId: Int, newUsername: String) {
        userDao.updateUsername(userId, newUsername)
    }

    suspend fun updatePassword(userId: Int, newPassword: String) {
        userDao.updatePassword(userId, newPassword)
    }

    suspend fun updateEmail(userId: Int, newEmail: String) {
        userDao.updateEmail(userId, newEmail)
    }

    suspend fun updateGender(userId: Int, newIsWoman: Boolean) {
        userDao.updateGender(userId, newIsWoman)
    }

}