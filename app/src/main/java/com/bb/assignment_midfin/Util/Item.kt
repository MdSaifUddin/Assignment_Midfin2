package com.bb.assignment_midfin.Util

import android.graphics.Bitmap
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "objects")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val name:String,
    val location:String,
    val cost:String,
    val image: Bitmap
)