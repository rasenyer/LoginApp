package com.rasenyer.loginapp.ui.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasenyer.loginapp.R
import com.rasenyer.loginapp.db.UserDatabase
import com.rasenyer.loginapp.repository.UserRepository
import com.rasenyer.loginapp.databinding.FragmentDetailsBinding
import com.rasenyer.loginapp.adapter.UserAdapter

class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        val application = requireNotNull(this.activity).application
        val userDao = UserDatabase.getInstance(application).userDao
        val userRepository = UserRepository(userDao)
        val detailsViewModelFactory = DetailsViewModelFactory(userRepository, application)

        detailsViewModel = ViewModelProvider(this, detailsViewModelFactory).get(DetailsViewModel::class.java)

        binding.lifecycleOwner = this

        setUpRecyclerView()

        return binding.root

    }

    private fun setUpRecyclerView() {

        binding.mRecyclerView.layoutManager = LinearLayoutManager(this.context)
        displayUsers()

    }

    private fun displayUsers() {

        detailsViewModel.getAll.observe(viewLifecycleOwner, {
            binding.mRecyclerView.adapter = UserAdapter(it)
        })

    }

}