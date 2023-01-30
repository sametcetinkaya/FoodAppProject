package com.sametcetinkaya.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sametcetinkaya.foodapp.data.Category
import com.sametcetinkaya.foodapp.data.Meal
import com.sametcetinkaya.foodapp.data.Over
import com.sametcetinkaya.foodapp.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
        ) : ViewModel()
{
    private val _getRandomMealLiveData = MutableLiveData<Meal>()
    val getRandomMealLiveData : LiveData<Meal> = _getRandomMealLiveData

        private var saveStateRandomMeal : Meal? = null
        fun getRandomMeal(){
            saveStateRandomMeal?.let { randomMeal ->
                _getRandomMealLiveData.postValue(randomMeal)
                return
            }
            viewModelScope.launch {
                try{
                    val response = homeRepository.getRandomMeal()
                    response.body()!!.meals.let {
                        _getRandomMealLiveData.postValue(it[0])
                    }
                    saveStateRandomMeal = response.body()!!.meals[0]
                }catch (t:Throwable)
                {
                    Log.d("testApp,",t.message.toString()+"Error Random Meal")
                }
            }
        }
    private val _getOverMealLiveData = MutableLiveData<List<Over>>()
    val getOverMealLiveData : LiveData<List<Over>> = _getOverMealLiveData

        fun getOverMeals(){
            viewModelScope.launch {
                try {
                    val response = homeRepository.getOverMeal("Seafood")
                    if (response.isSuccessful)
                    {
                        response.body()?.meals?.let {
                            _getOverMealLiveData.postValue(it)
                        }
                    }
                }catch (t:Throwable)
                {
                    Log.d("testApp,",t.message.toString()+"Error Over Data Meal")
                }
            }
        }
    private var _getCategoriesStateFlow = MutableStateFlow<List<Category>>(emptyList())
    var getCategoriesStateFlow : StateFlow<List<Category>> = _getCategoriesStateFlow

        fun getCategoriesHomeFragment() {
            viewModelScope.launch {
                try {
                    val response = homeRepository.getCategoriesHome()
                    if (response.isSuccessful)
                    {
                        response.body()?.categories.let { data ->
                            if (data != null) {
                                _getCategoriesStateFlow.emit(data)
                            }
                        }
                    }
                }catch (t:Throwable)
                {
                    Log.d("testApp,",t.message.toString()+"Error Categories")
                }
            }
        }
}