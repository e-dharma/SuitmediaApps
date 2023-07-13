package com.example.suitmediaapps.ui.third

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmediaapps.R
import com.example.suitmediaapps.databinding.ActivityThirdScreenBinding
import com.example.suitmediaapps.model.User
import com.example.suitmediaapps.model.UserAdapter
import com.example.suitmediaapps.util.ForResult

private const val USER_NAME = "userName"

class ThirdScreen : AppCompatActivity(), UserAdapter.OnUserClickListener, View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private var _binding: ActivityThirdScreenBinding? = null
    private val binding get() = _binding!!
    private val userAdapter = UserAdapter(this)
    private lateinit var thirdViewModel: ThirdViewModel
    private var page: Int = 1
    private var totalPages: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.toolbarUser) {
            tvTitleToolbar.text = getString(R.string.third_screen)
            imgBack.setOnClickListener(this@ThirdScreen)
        }
        binding.swipeRvUser.setOnRefreshListener(this)
        setupViewModel()
        setupRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onUserClick(data: User) {
        val userName = "${data.firstName} ${data.lastName}"
        setResult(RESULT_OK, Intent().putExtra(USER_NAME, userName))
        finish()
    }

    override fun onBackPressed() {
        setResult(RESULT_OK, Intent().putExtra(USER_NAME, intent.getStringExtra(USER_NAME)))
        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.toolbarUser.imgBack -> {
                onBackPressed()
            }
        }
    }

    override fun onRefresh() {
        userAdapter.resetData()
        page = 1
        thirdViewModel.getUsers(page++)
    }

    private fun setupViewModel() {
        thirdViewModel = ViewModelProvider(this)[ThirdViewModel::class.java]

        thirdViewModel.getUsers(page++)

        thirdViewModel.users.observe(this, {
            showEmpty(false)
            if (it.isEmpty()) {
                showEmpty(true)
            }
            userAdapter.addData(it)
        })

        thirdViewModel.totalPages.observe(this, {
            totalPages = it
        })

        thirdViewModel.loading.observe(this, {
            binding.swipeRvUser.isRefreshing = it
        })
    }

    private fun setupRecyclerView() {
        val rvScrollListener: RecyclerView.OnScrollListener =
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!recyclerView.canScrollVertically(1) && dy > 0 && page <= totalPages) {
                        thirdViewModel.getUsers(page++)
                    }

                    super.onScrolled(recyclerView, dx, dy)
                }
            }

        with(binding.rvUser) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
            addOnScrollListener(rvScrollListener)
        }
    }

    private fun showEmpty(state: Boolean) {
        binding.tvEmptyUser.visibility = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    class Contract : ActivityResultContract<String, ForResult>() {
        override fun createIntent(context: Context, input: String): Intent =
            Intent(context, ThirdScreen::class.java).putExtra(USER_NAME, input)

        override fun parseResult(resultCode: Int, intent: Intent?) = ForResult(
            resultCode == RESULT_OK,
            intent?.getStringExtra(USER_NAME).orEmpty()
        )
    }
}