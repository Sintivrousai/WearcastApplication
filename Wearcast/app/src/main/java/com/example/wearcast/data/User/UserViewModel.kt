package com.example.wearcast.data.User

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wearcast.data.AppDatabase
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    val readAllData: LiveData<List<User>>

    // MutableLiveData for the logged-in username
    private val _loggedInUsername = MutableLiveData<String>()
    val loggedInUsername: LiveData<String> get() = _loggedInUsername

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    suspend fun getUserIdByUsername(username: String): Int? {
        return repository.getUserIdByUsername(username)
    }

    fun addUser(user: User, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            // Check if the username is already taken
            if (repository.isUsernameTaken(user.username)) {
                onResult(false, "Username is already taken")
            } else {
                // Continue with your existing functionality (add the user)
                repository.addUser(user)
                _loggedInUsername.postValue(user.username)  // Set the logged-in username after sign-up
                onResult(true, "User added successfully")
            }
        }
    }

    // Function to validate user credentials and update the logged-in username
    fun validateUser(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repository.validateLogin(username, password)
            if (user != null) {
                _loggedInUsername.postValue(user.username) // Update logged-in username
                callback(true)
            } else {
                callback(false)
            }
        }
    }

    suspend fun getUserByUsername(username: String): User? {
        return repository.getUserByUsername(username)
    }

    suspend fun isUsernameTaken(username: String): Boolean {
        return repository.isUsernameTaken(username)
    }

    // Method to update the username in the database (this is already a suspend function)
    fun updateUsername(userId: Int, newUsername: String) {
        // Launch a coroutine in the ViewModel scope
        viewModelScope.launch {
            repository.updateUsername(userId, newUsername)
        }
    }

    // Method to update the password
    fun updatePassword(userId: Int, newPassword: String) {
        viewModelScope.launch {
            repository.updatePassword(userId, newPassword)
        }
    }

    // Method to update the email
    fun updateEmail(userId: Int, newEmail: String) {
        viewModelScope.launch {
            repository.updateEmail(userId, newEmail)
        }
    }

    // Method to update the gender
    fun updateGender(userId: Int, newIsWoman: Boolean) {
        viewModelScope.launch {
            repository.updateGender(userId, newIsWoman)
        }
    }

}