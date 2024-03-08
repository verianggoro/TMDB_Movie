package com.verianggoro.tmdbmovie.model

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("results") var movieList : ArrayList<Movie>? = null
)

data class Movie(
    @SerializedName("id") var idMovie : Int? = null,
    @SerializedName("original_title") var oriTitle : String? = null,
    @SerializedName("poster_path") var posterPath : String? = null,
    @SerializedName("release_date") var releaseDate : String? = null,
    @SerializedName("backdrop_path") var backdropPath : String? = null,
    @SerializedName("overview") var overview : String? = null,
    @SerializedName("tagline") var tagline : String? = null,
    @SerializedName("status") var status : String? = null,
)


