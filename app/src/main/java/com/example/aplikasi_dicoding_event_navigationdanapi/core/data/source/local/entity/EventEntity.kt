package com.example.aplikasi_dicoding_event_navigationdanapi.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "event")
data class EventEntity(
    @field:ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    var id: String = "",

    @field:ColumnInfo(name = "name")
    var name: String = "",

    @field:ColumnInfo(name = "mediaCover")
    var mediaCover: String? = null,

    @field:ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
): Parcelable