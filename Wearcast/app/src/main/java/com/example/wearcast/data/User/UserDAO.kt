package com.example.wearcast.data.User

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password LIMIT 1")
    suspend fun validateLogin(username: String, password: String): User?

    @Query("SELECT id FROM user_table WHERE username = :username")
    suspend fun getUserIdByUsername(username: String): Int?

    @Query("SELECT COUNT(*) FROM user_table WHERE username = :username")
    suspend fun isUsernameTaken(username: String): Boolean

    @Query("SELECT isWoman FROM user_table WHERE id = :userId LIMIT 1")
    suspend fun getIsWomanByUserId(userId: Int): Boolean

    @Query("UPDATE user_table SET password = :password, email = :email, isWoman = :isWoman WHERE id = :id")
    suspend fun updateUser(id: Int, password: String, email: String, isWoman: Boolean)

    // Method to update the username in the database
    @Query("UPDATE user_table SET username = :newUsername WHERE id = :userId")
    suspend fun updateUsername(userId: Int, newUsername: String)

    @Query("UPDATE user_table SET password = :newPassword WHERE id = :userId")
    suspend fun updatePassword(userId: Int, newPassword: String)

    @Query("UPDATE user_table SET email = :newEmail WHERE id = :userId")
    suspend fun updateEmail(userId: Int, newEmail: String)

    @Query("UPDATE user_table SET isWoman = :newIsWoman WHERE id = :userId")
    suspend fun updateGender(userId: Int, newIsWoman: Boolean)

    @Query("SELECT * FROM user_table WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?
}