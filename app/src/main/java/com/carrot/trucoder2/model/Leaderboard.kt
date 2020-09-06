package com.carrot.trucoder2.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "friendsLeaderboard")
@Parcelize
data class Leaderboard(
    @PrimaryKey
    val Name : String,
    val rank :String,
    val rating:Int,
    val platform:Int ,
    val avatar : String
): Parcelable