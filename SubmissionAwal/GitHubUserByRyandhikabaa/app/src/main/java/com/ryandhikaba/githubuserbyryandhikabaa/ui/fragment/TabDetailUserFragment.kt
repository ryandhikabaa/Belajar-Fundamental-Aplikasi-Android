package com.ryandhikaba.githubuserbyryandhikabaa.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ryandhikaba.githubuserbyryandhikabaa.R
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.ItemsItem
import com.ryandhikaba.githubuserbyryandhikabaa.data.response.UsersResponse
import com.ryandhikaba.githubuserbyryandhikabaa.data.retrofit.ApiConfig
import com.ryandhikaba.githubuserbyryandhikabaa.databinding.FragmentTabDetailUserBinding
import com.ryandhikaba.githubuserbyryandhikabaa.ui.DetailUserActivity
import com.ryandhikaba.githubuserbyryandhikabaa.ui.MainActivity
import com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModel.MainViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.ui.ViewModel.TabDetailUserViewModel
import com.ryandhikaba.githubuserbyryandhikabaa.ui.adapter.UsersAdapter
import com.ryandhikaba.githubuserbyryandhikabaa.utils.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TabDetailUserFragment : Fragment() {
    private lateinit var binding: FragmentTabDetailUserBinding
    private var position: Int = 0
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val tabDetailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            TabDetailUserViewModel::class.java)

        tabDetailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        tabDetailUserViewModel.showRecycler.observe(viewLifecycleOwner) {
            showRecycler(it)
        }

        tabDetailUserViewModel.showKeterangan.observe(viewLifecycleOwner) {
            showKeterangan(it)
        }

        tabDetailUserViewModel.listItem.observe(viewLifecycleOwner) { usersList ->
            setUsersData(usersList)
            usersList?.let {
                if (it.size == 0){
                        binding.tvKeterangan.visibility = View.VISIBLE
                }
            }
        }

        tabDetailUserViewModel.snackbarText.observe(viewLifecycleOwner) {

            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    requireView(),
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        with(binding){
            rvusers.isNestedScrollingEnabled = false
            val layoutManager = LinearLayoutManager(context)
            binding.rvusers.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
            binding.rvusers.addItemDecoration(itemDecoration)

            if (position == 1){
                tabDetailUserViewModel.fetchFollowers("$username")
                binding.tvKeterangan.text = "Tidak Memiliki Followers"
            } else {
                tabDetailUserViewModel.fetchFollowing("$username")
                binding.tvKeterangan.text = "Tidak Memiliki Following"

            }
        }
    }

    private fun setUsersData(usersItem: List<ItemsItem>) {
        val adapter = UsersAdapter()
        adapter.submitList(usersItem)
        binding.rvusers.adapter = adapter
        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(context, DetailUserActivity::class.java)
                intent.putExtra("USERS_CLICKED", data)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.divLoading.visibility = View.VISIBLE
        } else {
            binding.divLoading.visibility = View.GONE
        }
    }

    private fun showRecycler(isLoading: Boolean) {
        if (isLoading) {
            binding.rvusers.visibility = View.VISIBLE
        } else {
            binding.rvusers.visibility = View.GONE
        }
    }

    private fun showKeterangan(isLoading: Boolean) {
        if (isLoading) {
            binding.tvKeterangan.visibility = View.VISIBLE
        } else {
            binding.tvKeterangan.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
        private const val TAG = "RBA:TabDetailFragment ||  "
    }
}