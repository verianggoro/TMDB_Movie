package com.verianggoro.tmdbmovie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.verianggoro.tmdbmovie.R
import com.verianggoro.tmdbmovie.adapter.ViewPagerHome
import com.verianggoro.tmdbmovie.common.Config
import com.verianggoro.tmdbmovie.common.Utility
import com.verianggoro.tmdbmovie.databinding.ActivityMainBinding
import com.verianggoro.tmdbmovie.viewmodel.GenreViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: GenreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[GenreViewModel::class.java]
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        initial()
    }

    private fun initial(){
        val viewPager = binding.pagerMenu
        val tabLayout = binding.containerTab

        val params = hashMapOf(
            "language" to "en"
        )

        viewModel.sendingRequest(this, params, Utility.headers)
        viewModel.getListNews().observe(this){
            if (!it.genreList.isNullOrEmpty()){
                val adapter = ViewPagerHome(supportFragmentManager, lifecycle, it.genreList!!, it.genreList!!.size)
                viewPager.adapter = adapter
                viewPager.isUserInputEnabled = false

                TabLayoutMediator(tabLayout, viewPager){ tab, position ->
                    tab.text = it.genreList!![position].nameGenre
                    viewPager.currentItem = 0
                    tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                        override fun onTabSelected(tab: TabLayout.Tab?) {

                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                        }

                        override fun onTabReselected(tab: TabLayout.Tab?) {

                        }
                    })
                }.attach()

            }else{
                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
        }

    }
}