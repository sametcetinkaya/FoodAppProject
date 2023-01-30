package com.sametcetinkaya.foodapp.network

import com.sametcetinkaya.foodapp.data.CategoriesList
import com.sametcetinkaya.foodapp.data.MealList
import com.sametcetinkaya.foodapp.data.OverList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    suspend fun getRandomMeal() : Response<MealList>

    @GET("filter.php")
    suspend fun getOverMeals(
        @Query("c") categoryName : String
    ) : Response<OverList>

    @GET("categories.php")
    suspend fun getCategoriesHomeFragment() : Response<CategoriesList>
}