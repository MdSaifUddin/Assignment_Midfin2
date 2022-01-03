package com.bb.assignment_midfin.Util
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.sql.Blob

class BitmapConverter {

    @TypeConverter
    fun toByte(bitmap:Bitmap?): ByteArray? {
        if(bitmap!=null){
            var stream = ByteArrayOutputStream()
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream)
            var bitmapData = stream.toByteArray()
            return bitmapData
        }else{
            return null
        }

    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        if(byteArray!=null){
            var bitmap=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
            return bitmap
        }else{
            return null
        }

    }
}