package com.carrot.trucoder2.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carrot.trucoder2.model.*
import com.carrot.trucoder2.repository.CodeRespository
import com.carrot.trucoder2.utils.Resource
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel(val respository: CodeRespository) :ViewModel() {

    val result1: MutableLiveData<Int> = MutableLiveData();
    val result2: MutableLiveData<Int> = MutableLiveData();



    fun getCodeforcesUser(handle: String) = viewModelScope.launch {
        val response = respository.fetchCodeforcesUser(handle)
        if (response.isSuccessful) {
            response.body()?.let { result ->
                result1.postValue(1)
            }
        } else {
            result1.postValue(0)
        }
    }

    fun getCodeChefUser(handle: String) = viewModelScope.launch {
        val response = respository.fetchCodechefUser(handle)
        if (response.isSuccessful) {
            response.body()?.let { result ->
                result2.postValue(1)
            }
        } else {
            result2.postValue(0)
        }
    }
}