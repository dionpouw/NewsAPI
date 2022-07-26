package com.jeflette.newsapi.util

import androidx.room.TypeConverter
import com.jeflette.newsapi.data.entity.Source

class Converter {

    @TypeConverter
    fun fromSource(source: Source): String? {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String?): Source {
        return Source(name)
    }
}