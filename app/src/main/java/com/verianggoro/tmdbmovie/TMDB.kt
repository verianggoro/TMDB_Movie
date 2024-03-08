package com.verianggoro.tmdbmovie

import android.content.Context
import androidx.multidex.MultiDexApplication

class TMDB: MultiDexApplication() {
    companion object{
        lateinit var instance: TMDB private set
        fun getInstance(): Context?{
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}