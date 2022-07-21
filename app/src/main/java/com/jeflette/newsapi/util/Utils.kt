package com.jeflette.newsapi.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val date = format.parse(this) as Date
    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}