package com.verianggoro.tmdbmovie.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.verianggoro.tmdbmovie.common.Config
import com.verianggoro.tmdbmovie.common.OkHttpHelper
import com.verianggoro.tmdbmovie.common.Utility
import com.verianggoro.tmdbmovie.model.Reviews
import com.verianggoro.tmdbmovie.model.Videos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrailerViewModel: ViewModel() {
    private val TAG = TrailerViewModel::class.java.simpleName
    private var videosLiveData : MutableLiveData<Videos> = MutableLiveData()

    fun getListNews(): LiveData<Videos> = videosLiveData

    fun sendingRequest(context: Context, endpoints: String, params: HashMap<String, *>, headers: HashMap<String, String>){
        val form: HashMap<String, String?> = hashMapOf()
        Log.i(TAG, params.toString())
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val url = "${Config.Main_Url}${Config.DETAIL_PATH}/${endpoints}/${Config.VIDEO_TRAILER}"
                try {
                    val response = OkHttpHelper.executeGet(url, params, form, headers)
                    Log.i(TAG, response!!)
                    val gson = Gson()
                    val mapper = gson.fromJson(response, Videos::class.java)
                    videosLiveData.postValue(mapper)
                }catch (e: JsonSyntaxException){
                    withContext(Dispatchers.Main) {
                        Utility.showErrorDialog(context, e)
                    }
                }catch (e: Exception){
                    withContext(Dispatchers.Main) {
                        Utility.showErrorDialog(context, e)
                    }
                }
            }
        }
    }
}