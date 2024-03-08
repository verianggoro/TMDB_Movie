package com.verianggoro.tmdbmovie.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.verianggoro.tmdbmovie.R
import com.verianggoro.tmdbmovie.activity.DetailMovieActivity
import com.verianggoro.tmdbmovie.adapter.CardPosterAdapter
import com.verianggoro.tmdbmovie.common.Utility
import com.verianggoro.tmdbmovie.databinding.FragmentGeneralBinding
import com.verianggoro.tmdbmovie.model.Movie
import com.verianggoro.tmdbmovie.viewmodel.DiscoverViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentGeneral.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentGeneral : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var _binding: FragmentGeneralBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : DiscoverViewModel
    private lateinit var posterAdapter: CardPosterAdapter
    private var initialPage = 1
    private val layoutManager = GridLayoutManager(
        this.context,
        2
    )
    private lateinit var listMovieDiscover: ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentGeneralBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DiscoverViewModel::class.java]
        initialData(view)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentGeneral.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            FragmentGeneral().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }

    private fun initialData(view: View){
        binding.loadingBarHome.visibility = View.VISIBLE
        listMovieDiscover = ArrayList()
        val params = hashMapOf(
            "include_adult" to false,
            "include_video" to true,
            "language" to "en-US",
            "page" to initialPage,
            "sort_by" to "popularity.desc",
            "with_genres" to param1
        )
        viewModel.sendingRequest(view.context, params, Utility.headers)
        viewModel.getListNews().observe(requireActivity()){
            binding.loadingBarHome.visibility = View.GONE
            if (!it.movieList.isNullOrEmpty()){
                if (listMovieDiscover.isNullOrEmpty()){
                    listMovieDiscover = it.movieList!!
                    posterAdapter = CardPosterAdapter(listMovieDiscover)
                    binding.rvMovieList.adapter = posterAdapter
                }else{
                    listMovieDiscover.addAll(it.movieList!!)
                    posterAdapter.notifyDataSetChanged()
                }
                binding.rvMovieList.layoutManager = layoutManager
                binding.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                    }

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                        val sizer = listMovieDiscover.size
                        if (lastVisibleItemPosition == (sizer - 1)){
                            initialPage++
                            val paramss = hashMapOf(
                                "include_adult" to false,
                                "include_video" to true,
                                "language" to "en-US",
                                "page" to initialPage,
                                "sort_by" to "popularity.desc",
                                "with_genres" to param1
                            )
                            viewModel.sendingRequest(view.context, paramss, Utility.headers)
                        }
                    }
                })

                posterAdapter.setOnClicked(object : CardPosterAdapter.onItemCallback{
                    override fun itemEventClick(dataEvent: Movie) {
                        gotoDetail(dataEvent.idMovie!!)
                    }
                })
            }else{
                Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gotoDetail(idMovie: Int){
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(DetailMovieActivity.ID_MOVIE, idMovie)
        startActivity(intent)
    }
}