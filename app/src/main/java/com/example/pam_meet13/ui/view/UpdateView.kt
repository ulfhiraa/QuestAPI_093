package com.example.pam_meet13.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_meet13.ui.customwidget.CustomeTopAppBar
import com.example.pam_meet13.ui.navigasi.DestinasiNavigasi
import com.example.pam_meet13.ui.viewmodel.PenyediaViewModel
import com.example.pam_meet13.ui.viewmodel.UpdateViewModel
import kotlinx.coroutines.launch

//UpdateView untuk memperbarui data mahasiswa dengan ViewModel dan form input

// Menambahkan DestinasiUpdate sebagai navigasi untuk halaman Update
object DestinasiUpdate: DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Mahasiswa" // judul halaman
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateView( // untuk memperbarui data mahasiswa dengan navigasi kembali
    nim: String,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CustomeTopAppBar(
                title = "Update Mahasiswa",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryBody(
            insertUiState = updateViewModel.updateUiState,
            onMhsValueChange = updateViewModel::updateInsertMhsState,
            onSaveClick = {
                coroutineScope.launch {
                    updateViewModel.updateMhs()
                    onNavigateUp()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}