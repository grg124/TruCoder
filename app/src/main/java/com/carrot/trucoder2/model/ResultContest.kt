package com.carrot.trucoder2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.util.*

@Entity()
data class ResultContest(
    @PrimaryKey()
    val event: String,
    val duration: String,
    val end: String,
    val end_time: String,
    val href: String,
    val id: Int,
    val start: String,
    val start_time: String,
    @SerializedName("stamp")
    val timestamp : Long,
    val currentStatus : String,
    val endstamp:Long
)