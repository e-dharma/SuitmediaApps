package com.example.suitmediaapps.ui.second

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.suitmediaapps.model.User
import com.example.suitmediaapps.model.UserResponse
import com.example.suitmediaapps.remote.RetrofitConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondViewModel : ViewModel() {

    private val _loginName = MutableLiveData<String>()
    val loginName: LiveData<String> = _loginName
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    fun setLoginName(name: String){
        _loginName.postValue(name)
    }

    fun setUserName(name: String){
        _userName.postValue(name)
    }
}