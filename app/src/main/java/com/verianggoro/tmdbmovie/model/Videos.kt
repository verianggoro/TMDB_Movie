package com.verianggoro.tmdbmovie.model
import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results") var movieList : List<Video>? = null
)
data class Video(
    @SerializedName("key") var keyUrl : String? = null,
    @SerializedName("site") var siteSource : String? = null,
    @SerializedName("official") var officially : Boolean? = null,
)