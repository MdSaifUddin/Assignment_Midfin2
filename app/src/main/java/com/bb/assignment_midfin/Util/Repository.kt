package com.bb.assignment_midfin.Util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(val context: Context,val db:DAO) {
    init {
        CoroutineScope(Dispatchers.IO).launch {
            getData()
        }
    }
    suspend fun addItem(id:Long,name:String,location:String,cost:String,bitmap: Bitmap){
        try {
            db.insert(Item(id,name,location,cost,bitmap))
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context, "Item Added!", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Log.i("db",e.message.toString())
        }
    }

    suspend fun getData(){
        try {
            var temp=db.getData()
            list.postValue(temp)
        }catch (e:Exception){
            Log.i("db",e.message.toString())
        }
    }
    val list= MutableLiveData<List<Item>>()
}