package com.verianggoro.tmdbmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.verianggoro.tmdbmovie.common.Utility
import com.verianggoro.tmdbmovie.databinding.ItemWebTrailerBinding
import com.verianggoro.tmdbmovie.model.Video

class TrailerAdapter(
    private val listEvent: List<Video>
): RecyclerView.Adapter<TrailerAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrailerAdapter.EventViewHolder {
        return EventViewHolder(
            ItemWebTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TrailerAdapter.EventViewHolder, position: Int) {
        listEvent.let {
            holder.bind(it, position)
        }
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    inner class EventViewHolder(private val binding: ItemWebTrailerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: List<Video>, position: Int){
            binding.videoItem.settings.javaScriptEnabled = true
            binding.videoItem.settings.loadWithOverviewMode = true
            binding.videoItem.settings.useWideViewPort = true
            binding.videoItem.loadDataWithBaseURL(
                "https://www.youtube.com",
                Utility.getHtmlTrailer(data[position].keyUrl!!),
                "text/html",
                "UTF-8",
                null
            )
        }
    }

}

