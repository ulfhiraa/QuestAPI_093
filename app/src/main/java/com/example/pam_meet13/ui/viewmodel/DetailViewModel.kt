package com.example.pam_meet13.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pam_meet13.model.Mahasiswa
import com.example.pam_meet13.repository.MahasiswaRepository
import com.example.pam_meet13.ui.view.DestinasiDetail
import kotlinx.coroutines.launch

//  untuk mengelola status UI detail mahasiswa dan berinteraksi dengan repository untuk mengambil dan menghapus data mahasiswa
//  berdasarkan NIM


class DetailViewModel( savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository) : ViewModel()
{
    private val nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState())
        private set

    init {
        getMahasiswaByNim()
    }

     fun getMahasiswaByNim() {
         //  Mengambil data mahasiswa berdasarkan NIM dan mengupdate detailUiState.
         //  Menangani error jika permintaan gagal.
        viewModelScope.launch {
            detailUiState = DetailUiState(isLoading = true)
            try {
                val result = mahasiswaRepository.getMahasiswaByNim(nim)
                detailUiState = DetailUiState(
                    detailUiEvent = result.toDetailUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailUiState = DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown"
                )
            }
        }
    }

    fun deleteMhs() {
        // Menghapus data mahasiswa berdasarkan NIM dan mengupdate detailUiState sesuai hasilnya.
        viewModelScope.launch {
            detailUiState = DetailUiState(isLoading = true)
            try {
                mahasiswaRepository.deleteMahasiswa(nim)

                detailUiState = DetailUiState(isLoading = false)
            } catch (e: Exception) {
                detailUiState = DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown Error"
                )
            }
        }
    }
}

// menyimpan status UI untuk tampilan detail mahasiswa, kondisi loading, error, dan data mahasiswa.
data class DetailUiState(
    val detailUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEvent()
}

// Mengonversi objek Mahasiswa menjadi InsertUiEvent untuk digunakan dalam DetailUiState
fun Mahasiswa.toDetailUiEvent(): InsertUiEvent{
    return InsertUiEvent(
        nim = nim,
        nama = nama,
        jenisKelamin = jenisKelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan
    )
}