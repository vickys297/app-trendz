package com.vs.trendz.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/*
* Converter for builtBy array list
* */
class Converters {
    val gson = Gson()

    @TypeConverter
    fun fromBuildByList(list: ArrayList<BuildBy>): String {
        return gson.toJson(list)
    }


    @TypeConverter
    fun toBuildByList(json: String): ArrayList<BuildBy> {
        return gson.fromJson(json, object : TypeToken<ArrayList<BuildBy?>?>() {}.type)
    }

}


