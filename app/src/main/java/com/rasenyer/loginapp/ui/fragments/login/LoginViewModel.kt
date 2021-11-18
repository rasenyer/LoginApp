package com.rasenyer.loginapp.ui.fragments.login

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rasenyer.loginapp.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository, application: Application): AndroidViewModel(application), Observable {

    @Bindable
    val editTextUsername = MutableLiveData<String>()

    @Bindable
    val editTextPassword = MutableLiveData<String>()

    private val _errorFields = MutableLiveData<Boolean>()
    val errorFields: LiveData<Boolean>
        get() = _errorFields

    private val _errorUsername = MutableLiveData<Boolean>()
    val errorUsername: LiveData<Boolean>
        get() = _errorUsername

    private val _errorPassword = MutableLiveData<Boolean>()
    val errorPassword: LiveData<Boolean>
        get() = _errorPassword

    private val _navigateToDetails = MutableLiveData<Boolean>()
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    private val _navigateToRegister = MutableLiveData<Boolean>()
    val navigateToRegister: LiveData<Boolean>
        get() = _navigateToRegister

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    fun doneErrorFields() { _errorFields.value = false }

    fun doneErrorUsername() { _errorUsername.value = false }

    fun doneErrorPassword() { _errorPassword .value = false }

    fun doneNavigateToDetails() { _navigateToDetails.value = false }

    fun doneNavigateToRegister() { _navigateToRegister.value = false }

    fun login() {

        if (editTextUsername.value == null || editTextPassword.value == null) {

            _errorFields.value = true

        } else {

            coroutineScope.launch {

                val username = userRepository.getByUsername(editTextUsername.value!!)

                if (username != null) {

                    if(username.password == editTextPassword.value){

                        editTextUsername.value = null
                        editTextPassword.value = null

                        _navigateToDetails.value = true

                    } else {

                        _errorPassword.value = true

                    }

                } else {

                    _errorUsername.value = true

                }

            }

        }

    }

    fun register() { _navigateToRegister.value = true }

}