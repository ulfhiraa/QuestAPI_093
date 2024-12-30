package com.example.pam_meet13.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_meet13.repository.MahasiswaRepository
import com.example.pam_meet13.ui.view.DestinasiUpdate
import kotlinx.coroutines.launch

// untuk mengelola status UI pembaruan data dan berinteraksi dengan repository untuk memperbarui data
// berdasarkan nim

class UpdateViewModel(savedStateHandle: SavedStateHandle,
                      private val mahasiswaRepository: MahasiswaRepository): ViewModel()
{
    var updateUiState by mutableStateOf(InsertUiState())
        private set

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])

    init {
        viewModelScope.launch {
            updateUiState = mahasiswaRepository.getMahasiswaByNim(_nim)
                .toUiStateMhs()
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    fun updateMhs(){
        viewModelScope.launch {
            try {
                mahasiswaRepository.updateMahasiswa(_nim, updateUiState.insertUiEvent.toMhs())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
/*
memungkinkan mengambil data mahasiswa berdasarkan NIM,
memperbarui status UI dengan data yang diambil,
serta memperbarui data mahasiswa ke repository ketika pengguna mengirimkan perubahan.
*/