package com.example.apilist.data.model

import androidx.room.TypeConverter


//Type Converters
class Converters {
    @TypeConverter
    fun fromListToString(list: List<String>): String{
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(string: String): List<String>{
        return string.split(",")
    }
}
