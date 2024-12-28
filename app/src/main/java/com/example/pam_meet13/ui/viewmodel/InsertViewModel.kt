package com.example.pam_meet13.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_meet13.model.Mahasiswa
import com.example.pam_meet13.repository.MahasiswaRepository
import kotlinx.coroutines.launch

// implementasi InsertViewModel untuk mengelola proses input data Mahasiswa,
// termasuk data class InsertUiState dan InsertUiEvent
// serta fungsi ekstensi untuk konversi data antara InsertUiEvent dan Mahasiswa.

// untuk mengelola input data Mahasiswa melalui MahasiswaRepository.
class InsertViewModel(private val mhs: MahasiswaRepository) : ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertMhsState(insertUiEvent : InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertMhs() {
        viewModelScope.launch {
            try {
                mhs.insertMahasiswa(uiState.insertUiEvent.toMhs())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

// untuk merepresentasikan state UI pada proses input data Mahasiswa
data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

// untuk merepresentasikan event input data Mahasiswa dari UI
data class InsertUiEvent(
    val nim: String = "",
    val nama: String = "",
    val alamat: String = "",
    val jenisKelamin: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)

// untuk mengonversi InsertUiEvent menjadi Mahasiswa
fun InsertUiEvent.toMhs(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan,
)

fun Mahasiswa.toUiStateMhs(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

// fungsi ekstensi untuk mengonversi Mahasiswa menjadi InsertUiEvent
fun Mahasiswa.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan,
)
