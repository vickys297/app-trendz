package com.vs.trendz.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/*
* Data model for local storage and for api response data
*
* Unique for avoiding duplicate project name
* */


@Entity(tableName = "repo_local", indices = [Index(value = ["name"], unique = true)])
data class TrendingRepositoryResponseData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "avatar") val avatar: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "languageColor") val languageColor: String,
    @ColumnInfo(name = "stars") val stars: Int,
    @ColumnInfo(name = "forks") val forks: Int,
    @ColumnInfo(name = "currentPeriodStars") val currentPeriodStars: Int,
    @ColumnInfo(name = "buildBy") val builtBy: ArrayList<BuildBy>
) {
}

data class BuildBy(
    val href: String,
    val avatar: String,
    val username: String
)
