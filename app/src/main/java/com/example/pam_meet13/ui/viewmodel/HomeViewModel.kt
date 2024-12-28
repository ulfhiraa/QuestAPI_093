package com.example.pam_meet13.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.pam_meet13.model.Mahasiswa
import com.example.pam_meet13.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import java.io.IOException

// HomeViewModel untuk mengelola pengambilan data Mahasiswa
// dan state UI menggunakan sealed class HomeUiState.

// untuk merepresentasikan state aplikasi (Success, Error, Loading) dalam proses pengambilan data Mahasiswa.
sealed class HomeUiState {
    data class Success(val Mahasiswa: List<Mahasiswa>): HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

// untuk mengelola state UI dan mengambil data Mahasiswa dari MahasiswaRepository
class HomeViewModel (private val mhs: MahasiswaRepository): ViewModel(){
    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs() {
        viewModelScope.launch{
            mhsUIState = HomeUiState.Loading
            mhsUIState = try {
                HomeUiState.Success(mhs.getMahasiswa())
            } catch (e:IOException) {
                HomeUiState.Error
            } catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}
