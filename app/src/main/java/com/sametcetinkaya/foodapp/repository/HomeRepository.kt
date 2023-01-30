package com.sametcetinkaya.foodapp.repository

import android.util.Log
import com.sametcetinkaya.foodapp.data.CategoriesList
import com.sametcetinkaya.foodapp.data.MealList
import com.sametcetinkaya.foodapp.data.OverList
import com.sametcetinkaya.foodapp.network.MealApi
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val mealApi: MealApi
)
{
    suspend fun getRandomMeal() : Response<MealList>
    {
        val response = mealApi.getRandomMeal()
        if(response.isSuccessful)
        {
            Log.d("testApp","Success to connected to Rondom Meal")
            Log.d("testApp",response.code().toString())
        }
        else
        {
            Log.d("testApp","Failed to connected to Rondom Meal")
            Log.d("testApp",response.code().toString())
        }
        return response
    }

    suspend fun getOverMeal(categoryName : String) : Response<OverList>
    {
        val response = mealApi.getOverMeals(categoryName)
        if (response.isSuccessful)
        {
            Log.d("testApp", response.code().toString())
            Log.d("testApp", "Success to connected to over meals")
        }
        else
        {
            Log.d("testApp", response.code().toString())
            Log.d("testApp", "Failed to connected to over meals")
        }
        return response
    }

    suspend fun getCategoriesHome() : Response<CategoriesList>
    {
        val response = mealApi.getCategoriesHomeFragment()
        if (response.isSuccessful)
        {
            Log.d("testApp", response.code().toString())
            Log.d("testApp", "Success to connected to categories Home Fragment")
        }
        else
        {
            Log.d("testApp", response.code().toString())
            Log.d("testApp", "Failed to connected to categories Home Fragment")
        }
        return response
    }
}