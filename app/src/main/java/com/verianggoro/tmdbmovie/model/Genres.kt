package com.verianggoro.tmdbmovie.model

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("genres") var genreList : List<Genre>? = null
)

data class Genre(
    @SerializedName("id") var idGenre : Int? = null,
    @SerializedName("name") var nameGenre : String? = null
)

