package com.bb.assignment_midfin.Util

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DAO {

    @Insert
    fun insert(item:Item)

    @Query("select * from objects")
    fun getData():List<Item>
}