package com.rasenyer.loginapp.ui.fragments.login

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
import com.rasenyer.loginapp.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        val application = requireNotNull(this.activity).application
        val userDao = UserDatabase.getInstance(application).userDao
        val userRepository = UserRepository(userDao)
        val loginViewModelFactory = LoginViewModelFactory(userRepository, application)

        loginViewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)

        binding.mLoginViewModel = loginViewModel
        binding.lifecycleOwner = this

        loginViewModel.errorFields.observe(viewLifecycleOwner, { hasError ->

            if(hasError == true) {
                Toast.makeText(requireContext(), resources.getString(R.string.all_fields_are_required), Toast.LENGTH_SHORT).show()
                loginViewModel.doneErrorFields()
            }

        })

        loginViewModel.errorUsername.observe(viewLifecycleOwner, { hasError ->

            if(hasError == true) {
                Toast.makeText(requireContext(), resources.getString(R.string.username_does_not_exist_please_register), Toast.LENGTH_SHORT).show()
                loginViewModel.doneErrorUsername()
            }

        })

        loginViewModel.errorPassword.observe(viewLifecycleOwner, { hasError ->

            if(hasError == true) {
                Toast.makeText(requireContext(), resources.getString(R.string.your_password_is_wrong_please_check_your_password), Toast.LENGTH_SHORT).show()
                loginViewModel.doneErrorPassword()
            }

        })

        loginViewModel.navigateToDetails.observe(viewLifecycleOwner, { hasFinished ->

            if (hasFinished == true) {
                navigateToDetails()
                loginViewModel.doneNavigateToDetails()
            }

        })

        loginViewModel.navigateToRegister.observe(viewLifecycleOwner, { hasFinished ->

            if (hasFinished == true) {
                navigateToRegister()
                loginViewModel.doneNavigateToRegister()
            }

        })

        return binding.root

    }

    private fun navigateToDetails() {

        val action = LoginFragmentDirections.actionLoginFragmentToDetailsFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

    private fun navigateToRegister() {

        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        NavHostFragment.findNavController(this).navigate(action)

    }

}