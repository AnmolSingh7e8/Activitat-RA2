// File: CharacterApplication.kt
        package com.example.activitatra2.data.network

        import android.app.Application
        import androidx.room.Room
        import com.example.apilist.data.network.AppDatabase

class CharacterApplication : Application() {
            companion object {
                lateinit var database: AppDatabase
                    private set
            }

            override fun onCreate() {
                super.onCreate()
                database = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "starwars_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }