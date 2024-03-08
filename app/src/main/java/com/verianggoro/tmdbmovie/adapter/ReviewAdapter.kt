package com.verianggoro.tmdbmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.verianggoro.tmdbmovie.databinding.ItemUserReviewBinding
import com.verianggoro.tmdbmovie.model.Review

class ReviewAdapter(
    private val listEvent: List<Review>
): RecyclerView.Adapter<ReviewAdapter.EventViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewAdapter.EventViewHolder {
        return EventViewHolder(
            ItemUserReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewAdapter.EventViewHolder, position: Int) {
        listEvent.let {
            holder.bind(it, position)
        }
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    inner class EventViewHolder(private val binding: ItemUserReviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: List<Review>, position: Int){
            binding.authorReview.text = data[position].author
            binding.contentReview.text = data[position].content
            (data[position].authorDetail?.rating != null).also {
                if (it){
                    binding.ratingReview.rating = data[position].authorDetail?.rating!!.toFloat()
                }
            }
        }
    }

}

