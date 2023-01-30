package com.sametcetinkaya.foodapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.sametcetinkaya.foodapp.databinding.ActivityMealBinding

class MealActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMealBinding

    private lateinit var mealId :String
    private lateinit var mealThumb :String
    private lateinit var mealTitle :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

            getMealInformation()
    }

    private fun getMealInformation() {
        val intent = intent
            mealId = intent.getStringExtra("mealId").toString()
            mealThumb = intent.getStringExtra("mealThumb").toString()
            mealTitle = intent.getStringExtra("mealTitle").toString()

        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.mealImageView)

        binding.collapsingTool.title = mealTitle

        Log.d("testApp", mealId)
    }
}