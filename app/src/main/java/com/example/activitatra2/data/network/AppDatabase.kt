package com.example.apilist.data.network

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.activitatra2.data.model.nueva.Data
import com.example.apilist.data.model.Converters
import com.example.apilist.data.model.Item

@Database(entities = [Data::class], version = 1)
@TypeConverters(Converters::class) //Uso del convertidor de tipos
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    // Patrón singleton para garantizar que solo se cree una instancia de la base de datos
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Método para obtener la instancia de la base de datos
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}