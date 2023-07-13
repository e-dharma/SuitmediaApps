package com.example.suitmediaapps.ui.first

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.suitmediaapps.R
import com.example.suitmediaapps.databinding.ActivityFirstScreenBinding
import com.example.suitmediaapps.ui.second.SecondScreen
import com.example.suitmediaapps.util.isPalindrome

class FirstScreen : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityFirstScreenBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheckLogin.setOnClickListener(this)
        binding.btnNextLogin.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnCheckLogin -> {
                val check = binding.editPalindromeLogin.text.toString()
                if (check.isBlank()) {
                    binding.editPalindromeLogin.error = getString(R.string.input_error)
                } else {
                    if(check.isPalindrome()){
                        showToast(getString(R.string.is_palindrome))
                    }else{
                        showToast(getString(R.string.not_palindrome))
                    }
                }
            }
            binding.btnNextLogin -> {
                val loginName = binding.editNameLogin.text.toString()
                if (loginName.isBlank()) {
                    binding.editNameLogin.error = getString(R.string.input_error)
                } else {
                    val intent = Intent(this, SecondScreen::class.java)
                    intent.putExtra(SecondScreen.LOGIN_NAME, loginName)
                    startActivity(intent)
                }
            }
        }
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}