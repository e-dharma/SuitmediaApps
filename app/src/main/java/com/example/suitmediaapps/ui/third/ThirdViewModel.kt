package com.example.suitmediaapps.ui.third

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

private const val PER_PAGE = 10

class ThirdViewModel : ViewModel() {

    private val _users = MutableLiveData<ArrayList<User>>()
    val users: LiveData<ArrayList<User>> = _users
    private val _totalPages = MutableLiveData<Int>()
    val totalPages: LiveData<Int> = _totalPages
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getUsers(page: Int) {
        _loading.postValue(true)
        val client = RetrofitConfig.apiInstance.getUsers(page, PER_PAGE)

        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    _users.postValue(response.body()?.data)
                    _totalPages.postValue(response.body()?.totalPages)
                }
                _loading.postValue(false)
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("UserViewModel", "getUsers onFailure : ${t.message}")
                _loading.postValue(false)
            }
        })
    }
}