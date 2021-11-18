package com.rasenyer.loginapp.ui.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.rasenyer.loginapp.R
import com.rasenyer.loginapp.db.UserDatabase
import com.rasenyer.loginapp.repository.UserRepository
import com.rasenyer.loginapp.databinding.FragmentRegisterBinding

class RegisterFragment: Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        val application = requireNotNull(this.activity).application
        val userDao = UserDatabase.getInstance(application).userDao
        val userRepository = UserRepository(userDao)
        val registerViewModelFactory = RegisterViewModelFactory(userRepository, application)

        registerViewModel = ViewModelProvider(this, registerViewModelFactory).get(RegisterViewModel::class.java)

        binding.mRegisterViewModel = registerViewModel
        binding.lifecycleOwner = this

        registerViewModel.errorFields.observe(viewLifecycleOwner, { hasError ->

            if(hasError == true) {
                Toast.makeText(requireContext(), resources.getString(R.string.all_fields_are_required), Toast.LENGTH_SHORT).show()
                registerViewModel.doneErrorFields()
            }

        })

        registerViewModel.errorUsername.observe(viewLifecycleOwner, { hasError ->

            if(hasError == true){
                Toast.makeText(requireContext(), resources.getString(R.string.username_already_registered_try_another), Toast.LENGTH_SHORT).show()
                registerViewModel.doneErrorUserName()
            }

        })

        registerViewModel.navigation.observe(viewLifecycleOwner, { hasFinished ->

            if (hasFinished == true){
                navigation()
                registerViewModel.doneNavigation()
            }

        })

        return binding.root

    }

    private fun navigation() {

        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

}