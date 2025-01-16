package com.example.wearcast

import android.app.Application
import android.content.Context
import com.example.wearcast.OutfitCombinations.OutfitRepository
import com.example.wearcast.data.AppDatabase
import com.example.wearcast.data.Bottoms.BottomsRepository
import com.example.wearcast.data.Tops.TopsRepository
import com.example.wearcast.data.Jackets.JacketsRepository
import com.example.wearcast.data.User.UserRepository
import com.example.wearcast.data.upload.WardrobeRepository

class WearcastApplication : Application() {
    lateinit var topsRepository: TopsRepository
    lateinit var bottomsRepository: BottomsRepository
    lateinit var jacketsRepository: JacketsRepository
    lateinit var outfitRepository: OutfitRepository
    lateinit var wardrobeRepository: WardrobeRepository
    lateinit var userRepository: UserRepository // Add UserRepository

    override fun onCreate() {
        super.onCreate()
        appContext = this

        // Initialize repositories
        val database = AppDatabase.getDatabase(this)
        topsRepository = TopsRepository(database.topsDao())
        bottomsRepository = BottomsRepository(database.bottomsDao())
        jacketsRepository = JacketsRepository(database.jacketsDao())

        // Initialize UserRepository
        userRepository = UserRepository(database.userDao())

        // Initialize WardrobeRepository with UserDao
        wardrobeRepository = WardrobeRepository(
            database.wardrobeDao(),
            database.topsDao(),
            database.bottomsDao(),
            database.jacketsDao(),
            database.userDao() // Pass UserDao here
        )

        outfitRepository = OutfitRepository(wardrobeRepository)
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}
