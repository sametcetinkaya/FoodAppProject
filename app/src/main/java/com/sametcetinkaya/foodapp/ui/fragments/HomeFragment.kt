package com.sametcetinkaya.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sametcetinkaya.foodapp.adapters.CategoriesHomeAdapter
import com.sametcetinkaya.foodapp.adapters.OverAdapter
import com.sametcetinkaya.foodapp.databinding.FragmentHomeBinding
import com.sametcetinkaya.foodapp.ui.activities.MealActivity
import com.sametcetinkaya.foodapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel : HomeViewModel by viewModels()
    private lateinit var overAdapter : OverAdapter
    private lateinit var categoriesHomeAdapter : CategoriesHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overAdapter = OverAdapter()
        categoriesHomeAdapter = CategoriesHomeAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRandomMeal()
        getOverMeals()
        setupOverRecyclerView()
        getCategories()
        setupCategoriesRecyclerView()
    }

    private fun setupCategoriesRecyclerView() {
        binding.categoriesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3 , RecyclerView.VERTICAL , false)
            adapter = categoriesHomeAdapter
        }
    }

    private fun getCategories() {
        homeViewModel.getCategoriesHomeFragment()
        lifecycleScope.launchWhenStarted {
            homeViewModel.getCategoriesStateFlow.collect{ data ->
                categoriesHomeAdapter.differ.submitList(data)
            }
        }
    }

    private fun getRandomMeal() {
        homeViewModel.getRandomMeal()
        homeViewModel.getRandomMealLiveData.observe(viewLifecycleOwner){data ->
            Glide.with(this)
                .load(data.strMealThumb)
                .into(binding.randomImage)
            try {
                binding.homeCardView.setOnClickListener{
                    val intent = Intent (context, MealActivity::class.java)
                    intent.putExtra("mealId" , data.idMeal)
                    intent.putExtra("mealTitle" , data.strMeal)
                    intent.putExtra("mealThumb" , data.strMealThumb)
                    startActivity(intent)
                }
            }catch (t:Throwable)
            {
                Log.d("testApp", t.message.toString())
            }
        }
    }

    private fun setupOverRecyclerView() {
        binding.overRecyclerView.apply {
            layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL , false)
            adapter = overAdapter
        }
    }

    private fun getOverMeals() {
        homeViewModel.getOverMeals()
        homeViewModel.getOverMealLiveData.observe(viewLifecycleOwner){data ->
            overAdapter.differ.submitList(data)
        }
    }
}