package com.example.suitmediaapps.ui.second

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.suitmediaapps.R
import com.example.suitmediaapps.databinding.ActivitySecondScreenBinding
import com.example.suitmediaapps.ui.third.ThirdScreen

class SecondScreen : AppCompatActivity(), View.OnClickListener{

    private var _binding: ActivitySecondScreenBinding? = null

    private val binding get() = _binding!!
    private val userContract = registerForActivityResult(ThirdScreen.Contract()) {
        secondViewModel.setUserName(it.result)
    }
    private lateinit var secondViewModel: SecondViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarMain.tvTitleToolbar.text = getString(R.string.second_screen)
        binding.btnChooseMain.setOnClickListener(this)
        binding.toolbarMain.imgBack.setOnClickListener(this)
        setupViewModel()
    }

    private fun setupViewModel() {
        secondViewModel = ViewModelProvider(this)[SecondViewModel::class.java]

        secondViewModel.loginName.observe(this, {
            binding.tvUsernameMain.text = it
        })

        intent.getStringExtra(LOGIN_NAME)?.let { secondViewModel.setLoginName(it) }

        secondViewModel.userName.observe(this, {
            binding.tvSelectedUsernameMain.text = it
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnChooseMain -> {
                userContract.launch(binding.tvSelectedUsernameMain.text.toString())
            }
            binding.toolbarMain.imgBack -> {
                onBackPressed()
            }
        }
    }

    companion object {
        const val LOGIN_NAME = "loginName"
    }
}