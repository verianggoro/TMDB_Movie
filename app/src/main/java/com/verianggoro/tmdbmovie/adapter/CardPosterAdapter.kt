package com.verianggoro.tmdbmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.verianggoro.tmdbmovie.R
import com.verianggoro.tmdbmovie.common.Config
import com.verianggoro.tmdbmovie.databinding.ItemCardPosterBinding
import com.verianggoro.tmdbmovie.model.Movie
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CardPosterAdapter(
    private val listEvent: ArrayList<Movie>
): RecyclerView.Adapter<CardPosterAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardPosterAdapter.EventViewHolder {
        return EventViewHolder(
            ItemCardPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CardPosterAdapter.EventViewHolder, position: Int) {
        listEvent.let {
            holder.bind(it, position)
            holder.itemView.setOnClickListener { onCallBack?.itemEventClick(listEvent[position]) }
        }
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    inner class EventViewHolder(private val binding: ItemCardPosterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: List<Movie>, position: Int){
            binding.titleMovie.text = data[position].oriTitle
            binding.dateRelease.text = data[position].releaseDate
            Glide.with(binding.root)
                .load(Config.BASE_URL_ASSETS+data[position].posterPath)
                .error(R.drawable.image_break)
                .into(binding.imgPoster)
        }
    }


    private var onCallBack: onItemCallback? = null
    fun setOnClicked(onItemCallback: onItemCallback){
        this.onCallBack = onItemCallback
    }

    interface onItemCallback{
        fun itemEventClick(dataEvent: Movie)
    }
}

