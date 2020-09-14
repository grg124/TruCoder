package com.carrot.trucoder2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carrot.trucoder2.repository.CodeRespository
import kotlinx.coroutines.launch

class DetailsViewModel(private val respository: CodeRespository) :ViewModel() {

    val result1: MutableLiveData<Int> = MutableLiveData();
    val result2: MutableLiveData<Int> = MutableLiveData();

    fun getCodeforcesUser(handle: String) = viewModelScope.launch {
        val response = respository.fetchFriendListCF(handle)
        if (response.isSuccessful) {
            response.body()?.let {
                if(it.status == "Success")
                    result1.postValue(1)
                else
                    result1.postValue(0)
            }
        } else {
            result1.postValue(0)
        }
    }

    fun getCodeChefUser(handle: String) = viewModelScope.launch {
        val response = respository.fetchFriendListCC(handle)
        if (response.isSuccessful) {
            response.body()?.let {
                if(it.status == "Success")
                    result2.postValue(1)
                else
                    result2.postValue(0)
            }
        } else {
            result2.postValue(0)
        }
    }
}