package com.rasenyer.loginapp.ui.fragments.register

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.rasenyer.loginapp.model.User
import com.rasenyer.loginapp.repository.UserRepository
import kotlinx.coroutines.*

class RegisterViewModel(private val userRepository: UserRepository, application: Application): AndroidViewModel(application), Observable {

    @Bindable
    val editTextLink = MutableLiveData<String>()

    @Bindable
    val editTextName = MutableLiveData<String>()

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

    private val _navigation = MutableLiveData<Boolean>()
    val navigation: LiveData<Boolean>
        get() = _navigation

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    fun doneErrorFields() { _errorFields.value = false }

    fun doneErrorUserName() { _errorUsername.value = false }

    fun doneNavigation() { _navigation.value = false }

    fun register() {

        if (editTextLink.value == null || editTextName.value == null || editTextUsername.value == null || editTextPassword.value == null) {

            _errorFields.value = true

        } else {

            coroutineScope.launch {

                val usernameObtained = userRepository.getByUsername(editTextUsername.value!!)

                if (usernameObtained != null) {

                    _errorUsername.value = true

                } else {

                    val link        =   editTextLink.value!!
                    val name        =   editTextName.value!!
                    val username    =   editTextUsername.value!!
                    val password    =   editTextPassword.value!!

                    insert(User(id = 0, link = link, name = name, username = username, password = password))

                    this@RegisterViewModel.editTextLink.value       =   null
                    this@RegisterViewModel.editTextName.value       =   null
                    this@RegisterViewModel.editTextUsername.value   =   null
                    this@RegisterViewModel.editTextPassword.value   =   null

                    _navigation.value = true

                }

            }

        }

    }

    private fun insert(user: User): Job = viewModelScope.launch { userRepository.insert(user) }

}





