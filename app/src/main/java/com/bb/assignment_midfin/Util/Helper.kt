package com.bb.assignment_midfin.Util

import android.content.Context
import androidx.room.*

@Database(entities = [Item::class], version = 1)
@TypeConverters(BitmapConverter::class)
abstract class Helper:RoomDatabase() {
    abstract fun getDao():DAO

    companion object{
        @Volatile
        var INSTANCE:Helper?=null

        fun getInstance(context: Context):Helper{
            synchronized(this){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(
                        context,
                        Helper::class.java,
                        "objects"
                    ).build()
                }
                return INSTANCE!!
            }
        }
    }
}