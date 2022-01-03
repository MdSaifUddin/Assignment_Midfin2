package com.bb.assignment_midfin.ViewModel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.assignment_midfin.Util.Item
import com.bb.assignment_midfin.Util.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val repository: Repository):ViewModel() {

    fun addItem(id:Long,name:String,location:String,cost:String,bitmap: Bitmap){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addItem(id,name,location,cost,bitmap)
            repository.getData()
        }
    }

    var list=MutableLiveData<List<Item>>()
    get()=repository.list
    //////////////////////////
}