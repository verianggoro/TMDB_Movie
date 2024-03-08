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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReviewViewModel: ViewModel() {
    private val TAG = ReviewViewModel::class.java.simpleName
    private var movieReview : MutableLiveData<Reviews> = MutableLiveData()

    fun getListNews(): LiveData<Reviews> = movieReview

    fun sendingRequest(context: Context, endpoints: String, params: HashMap<String, *>, headers: HashMap<String, String>){
        val form: HashMap<String, String?> = hashMapOf()
        Log.i(TAG, params.toString())
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val url = "${Config.Main_Url}${Config.DETAIL_PATH}/${endpoints}/${Config.REVIEW_USER}"
                try {
                    val response = OkHttpHelper.executeGet(url, params, form, headers)
                    Log.i(TAG, response!!)
                    val gson = Gson()
                    val mapper = gson.fromJson(response, Reviews::class.java)
                    movieReview.postValue(mapper)
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