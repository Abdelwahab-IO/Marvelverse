package com.example.marvelverse.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("EVENT_TABLE")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String?,
    val series: String?,
    val comics: String?,
    val creators: String?,
    val stories: String?,
    val characters: String?,
    val thumbnail: String?,
) : Serializable
