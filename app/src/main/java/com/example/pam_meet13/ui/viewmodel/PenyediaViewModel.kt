package com.example.pam_meet13.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pam_meet13.MahasiswaApplications

//  untuk membuat instance HomeViewModel dan InsertViewModel dengan dependensi MahasiswaRepository
object PenyediaViewModel{
    val Factory = viewModelFactory {
        // HOME
        initializer {
            HomeViewModel(
                MahasiswaApplications()
                    .container
                    .mahasiswaRepository
            )
        }

        // INSERT
        initializer {
            InsertViewModel(
                MahasiswaApplications()
                    .container
                    .mahasiswaRepository
            )
        }

        // DETAIL
        initializer {
            DetailViewModel(
                createSavedStateHandle(),
                MahasiswaApplications()
                    .container
                    .mahasiswaRepository
            )
        }

        // UPDATE
        initializer {
            UpdateViewModel(
                createSavedStateHandle(),
                MahasiswaApplications()
                    .container
                    .mahasiswaRepository
            )
        }
    }

    fun CreationExtras.MahasiswaApplications(): MahasiswaApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)
}