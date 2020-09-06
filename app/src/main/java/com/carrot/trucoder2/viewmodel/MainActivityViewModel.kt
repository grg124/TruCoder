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

class MainActivityViewModel(val respository: CodeRespository) :ViewModel() {

    val codeforcesUserLD: MutableLiveData<Resource<ResponseCodforces>> = MutableLiveData()
    val codechefUserLD : MutableLiveData<Resource<ResponseCodechef>> = MutableLiveData()
    val contestLD : MutableLiveData<Resource<List<ResultContest>>> = MutableLiveData();
    val codechefFriendsLD : MutableLiveData<Resource<ResponseLeaderboard>> = MutableLiveData();
    val codeforcesFriendsLD : MutableLiveData<Resource<ResponseLeaderboard>> = MutableLiveData();


    init {
        getContestData()

    }



    fun RefreshCCFriends(handles: String) = viewModelScope.launch {
        val list = respository.RefreshCC()
        var str = ""
        for(i in list)
            str = "$str$i;"
        if(list.size == 0)
            str = "$handles;"
        val response = respository.fetchFriendListCC(str)
        if(response.isSuccessful){
            response.body()?.let {
                respository.InsertFriends(it)
            }
        }
        else{
            Timber.e("Could not refresh Codechef friends list")
        }
    }
    fun RefreshCFFriends(handles: String) = viewModelScope.launch {
        val list = respository.RefreshCF()
        var str = ""
        for(i in list)
            str = "$str$i;"
        if(list.size == 0)
            str = "$handles;"
        val response = respository.fetchFriendListCF(str)
        if(response.isSuccessful){
            response.body()?.let {
                respository.InsertFriends(it)
            }
        }
        else{
            Timber.e("Could not refresh Codeforces friends list")
        }
    }

    fun getCodeforcesUser(handle : String) = viewModelScope.launch {
        codeforcesUserLD.postValue(Resource.Loading())
        val response = respository.fetchCodeforcesUser(handle)
        if(response.isSuccessful) {
            response.body()?.let { result ->
                codeforcesUserLD.postValue(Resource.Success(result))
            }
        }
        else{
            codeforcesUserLD.postValue(Resource.Error(null, response.message()))
        }
    }

    fun getCodeChefUser(handle:String) = viewModelScope.launch {
        codechefUserLD.postValue(Resource.Loading())
        val response = respository.fetchCodechefUser(handle)
        if(response.isSuccessful) {
            response.body()?.let { result ->
                codechefUserLD.postValue(Resource.Success(result))
            }
        }
        else{
            codechefUserLD.postValue(Resource.Error(null, response.message()))
        }
    }

    fun getContestData() = viewModelScope.launch {
        contestLD.postValue(Resource.Loading())
        val response = respository.fetchContests()
        if(response.isSuccessful){
            response.body()?.let {
                respository.NukeContests()
                respository.InsertContests(it.resultContest)
            }
        }
        else{
            contestLD.postValue(Resource.Error(null , response.message()))
        }
    }

    fun getCodechefFriends(handles :String) = viewModelScope.launch {
        codechefFriendsLD.postValue(Resource.Loading())
        val response = respository.fetchFriendListCC(handles)
        if(response.isSuccessful){
            response.body()?.let {
                respository.InsertFriends(it)
                codechefFriendsLD.postValue(Resource.Success(it))
            }
        }
        else{
            codechefFriendsLD.postValue(Resource.Error(null , response.message()))
        }
    }

    fun getCodeforcesFriends(handles : String) = viewModelScope.launch {
        codeforcesUserLD.postValue(Resource.Loading())
        val response = respository.fetchFriendListCF(handles)
        if(response.isSuccessful){
            response.body()?.let {
                respository.InsertFriends(it)
                codeforcesFriendsLD.postValue(Resource.Success(it))
            }
        }
        else{
            codeforcesFriendsLD.postValue(Resource.Error(null , response.message()))
        }
    }

    fun getCodechefContests() =
        respository.getCodechefContests()

    fun getCodeforcesContest() =
        respository.getCodeforcesContests()

    fun getGoogleContests() =
        respository.getGoogleContests()

    fun getAllContests() =
        respository.getAllContests()

    fun getTopCoderContests()=
        respository.getTopcoderContest()

    fun getAllCCFriends() =
        respository.getAllCCFriends()

    fun getAllCFFriends() =
        respository.getAllCFFriends()

    fun getAllRunningCCContests()=
        respository.getAllRunningCCContest()

    fun getAllRunningCFContests()=
        respository.getAllRunningCFContest()

    fun getAllRunningGContests()=
        respository.getAllRunningGContest()

    fun getAllRunningTContests()=
        respository.getAllRunningTContest()

    fun getAllRunningContests()=
        respository.getAllRunningContest()

    fun InsertFriend(leaderboard: ResponseLeaderboard)= viewModelScope.launch {
        respository.InsertFriends(leaderboard)
    }


    fun DeleteFriends(leaderboard: Leaderboard) = viewModelScope.launch {
        respository.DeleteFriends(leaderboard)
    }



}