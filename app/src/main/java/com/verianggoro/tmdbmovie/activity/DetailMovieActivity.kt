package com.verianggoro.tmdbmovie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.verianggoro.tmdbmovie.R
import com.verianggoro.tmdbmovie.adapter.CardPosterAdapter
import com.verianggoro.tmdbmovie.adapter.ReviewAdapter
import com.verianggoro.tmdbmovie.adapter.TrailerAdapter
import com.verianggoro.tmdbmovie.common.Config
import com.verianggoro.tmdbmovie.common.Utility
import com.verianggoro.tmdbmovie.databinding.ActivityDetailMovieBinding
import com.verianggoro.tmdbmovie.viewmodel.DetailMovieViewModel
import com.verianggoro.tmdbmovie.viewmodel.GenreViewModel
import com.verianggoro.tmdbmovie.viewmodel.ReviewViewModel
import com.verianggoro.tmdbmovie.viewmodel.TrailerViewModel

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    var idMovies : Int? = null
    private lateinit var viewModel: DetailMovieViewModel
    private lateinit var viewModelReview: ReviewViewModel
    private lateinit var viewModelTrailer: TrailerViewModel
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var trailerAdapter: TrailerAdapter

    companion object {
        const val ID_MOVIE = "idMovie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie)
        viewModel = ViewModelProvider(this)[DetailMovieViewModel::class.java]
        viewModelReview = ViewModelProvider(this)[ReviewViewModel::class.java]
        viewModelTrailer = ViewModelProvider(this)[TrailerViewModel::class.java]

        if (intent.extras != null) {
            idMovies = intent.getIntExtra(ID_MOVIE, 0)
            initialData(idMovies!!)
        }

    }

    private fun initialData(idMovie: Int){
        binding.loadingBarDetail.visibility = View.VISIBLE
        val params = hashMapOf<String, String>()
        viewModel.sendingRequest(this, idMovie.toString(), params, Utility.headers)
        viewModelReview.sendingRequest(this, idMovie.toString(), params, Utility.headers)
        viewModelTrailer.sendingRequest(this, idMovie.toString(), params, Utility.headers)
        viewModel.getListNews().observe(this){
            binding.loadingBarDetail.visibility = View.GONE
            Glide.with(binding.root)
                .load(Config.BASE_URL_ASSETS+it.backdropPath)
                .error(R.drawable.image_break)
                .into(binding.backdropMovie)

            Glide.with(binding.root)
                .load(Config.BASE_URL_ASSETS+it.posterPath)
                .error(R.drawable.image_break)
                .into(binding.posterDetail)

            binding.titleDetail.text = it.oriTitle
            binding.releaseDetail.text = it.releaseDate
            binding.overviewDetail.text = it.overview
        }
        viewModelReview.getListNews().observe(this){
            if (!it.results.isNullOrEmpty()){
                reviewAdapter = ReviewAdapter(it.results!!)
                binding.rvUserReview.adapter = reviewAdapter
                binding.rvUserReview.layoutManager = LinearLayoutManager(
                    this, LinearLayoutManager.HORIZONTAL, false
                )
            }else{
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
        }
        viewModelTrailer.getListNews().observe(this){
            if (!it.movieList.isNullOrEmpty()){
                trailerAdapter = TrailerAdapter(it.movieList!!)
                binding.rvTrailer.adapter = trailerAdapter
                binding.rvTrailer.layoutManager = LinearLayoutManager(
                    this, LinearLayoutManager.HORIZONTAL, false
                )
            }else{
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}