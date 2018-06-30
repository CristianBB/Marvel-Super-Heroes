package com.costular.marvelheroes.data.db

import android.arch.persistence.room.TypeConverter

class Converters {

    @TypeConverter
    fun arrayToString(value: Array<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun stringToArray(value: String): Array<String> {
        return value.split(",").toTypedArray()
    }
}