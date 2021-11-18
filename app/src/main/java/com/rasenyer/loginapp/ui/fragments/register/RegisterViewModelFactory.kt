package com.rasenyer.loginapp.ui.fragments.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rasenyer.loginapp.repository.UserRepository
import java.lang.IllegalArgumentException

class RegisterViewModelFactory(private  val repository: UserRepository, private val application: Application):ViewModelProvider.Factory{

    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
           return RegisterViewModel(repository, application) as T
       }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}