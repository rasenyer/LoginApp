package com.rasenyer.loginapp.ui.fragments.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rasenyer.loginapp.repository.UserRepository

class DetailsViewModel (userRepository: UserRepository, application: Application): AndroidViewModel(application) {
    val getAll = userRepository.getAll
}