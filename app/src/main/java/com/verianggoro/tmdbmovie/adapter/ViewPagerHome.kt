package com.verianggoro.tmdbmovie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.verianggoro.tmdbmovie.fragment.FragmentGeneral
import com.verianggoro.tmdbmovie.model.Genre

class ViewPagerHome(fragmentManager: FragmentManager,
                    lifecycle: Lifecycle,
                    private var fragmentData: List<Genre>,
                    private var numPage: Int): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return numPage
    }

    override fun createFragment(position: Int): Fragment {
        return FragmentGeneral.newInstance(fragmentData[position].idGenre!!, "Home")
    }
}