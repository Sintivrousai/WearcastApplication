package com.example.wearcast.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wearcast.data.Bottoms.Bottoms
import com.example.wearcast.data.Bottoms.BottomsDao
import com.example.wearcast.data.Tops.Tops
import com.example.wearcast.data.Tops.TopsDao
import com.example.wearcast.data.User.User
import com.example.wearcast.data.User.UserDao
import com.example.wearcast.data.Jackets.Jackets
import com.example.wearcast.data.Jackets.JacketsDao
import com.example.wearcast.data.upload.Wardrobe
import com.example.wearcast.data.upload.WardrobeDao

@Database(
    entities = [User::class, Tops::class, Bottoms::class, Jackets::class, Wardrobe::class], // Updated with the new User class
    version = 11,  // Increment version to 11 for the schema update
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun topsDao(): TopsDao
    abstract fun bottomsDao(): BottomsDao
    abstract fun jacketsDao(): JacketsDao
    abstract fun wardrobeDao(): WardrobeDao // Added DAO for wardrobe_table

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        private val MIGRATION_10_11 = object : Migration(10, 11) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE user_table_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        username TEXT NOT NULL,
                        password TEXT NOT NULL,
                        email TEXT NOT NULL,
                        isWoman INTEGER NOT NULL DEFAULT 0
                    )
                """)

                database.execSQL("""
                    INSERT INTO user_table_new (id, username, password, email, isWoman)
                    SELECT id, username, password, '', 0 FROM user_table
                """)

                database.execSQL("DROP TABLE user_table")
                database.execSQL("ALTER TABLE user_table_new RENAME TO user_table")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                )
                    .addMigrations(MIGRATION_10_11)  // Add both migrations
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
