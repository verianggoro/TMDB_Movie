package com.verianggoro.tmdbmovie.model

import com.google.gson.annotations.SerializedName

data class Reviews(
    @SerializedName("results") var results : List<Review>? = null,
)

data class Review(
    @SerializedName("author") var author : String? = null,
    @SerializedName("content") var content : String? = null,
    @SerializedName("created_at") var createdAt : String? = null,
    @SerializedName("author_details") var authorDetail : User? = null,
)

data class User(
    @SerializedName("name") var authorName : String? = null,
    @SerializedName("userName") var content : String? = null,
    @SerializedName("rating") var rating : Int? = null,
)
