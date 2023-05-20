package com.example.marvelverse.data.dataSources.local.entities.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("KEYWORD_SEARCH_TABLE")
data class SearchKeywordEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null  ,
    val keyword: String,
    val timestamp: Long,
) {

}