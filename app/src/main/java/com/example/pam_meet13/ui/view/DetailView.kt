package com.example.pam_meet13.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pam_meet13.model.Mahasiswa
import com.example.pam_meet13.ui.customwidget.CustomeTopAppBar
import com.example.pam_meet13.ui.navigasi.DestinasiNavigasi
import com.example.pam_meet13.ui.viewmodel.DetailUiState
import com.example.pam_meet13.ui.viewmodel.DetailViewModel
import com.example.pam_meet13.ui.viewmodel.PenyediaViewModel
import com.example.pam_meet13.ui.viewmodel.toMhs

// DetailView untuk mengatur navigasi, menampilkan, edit dan hapus data

// Menambahkan DestinasiDetail sebagai navigasi untuk layar Detail
object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Mahasiswa"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView( // untuk menampilkan detail mahasiswa dengan opsi edit dan delete
    nim: String,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit = { },
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getMahasiswaByNim()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onEditClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa"
                )
            }
        }
    ) { innerPadding ->
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        BodyDetailMhs(
            detailUiState = viewModel.detailUiState,
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = {
                deleteConfirmationRequired = true
            }
        )

        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    viewModel.deleteMhs()
                    onDeleteClick()
                    deleteConfirmationRequired = false
                },
                onDeleteCancel = {
                    deleteConfirmationRequired = false
                },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun BodyDetailMhs( // untuk menampilkan data detail mahasiswa berdasarkan state UI
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState,
    onDeleteClick: () -> Unit
) {
    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMhs(
                    mahasiswa = detailUiState.detailUiEvent.toMhs(),
                    modifier = modifier
                )

                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = onDeleteClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }
            }
        }
    }
}

@Composable
fun ItemDetailMhs( // untuk menampilkan informasi mahasiswa dalam kartu
    modifier: Modifier = Modifier,
    mahasiswa: Mahasiswa,
){
    Card(
        modifier = modifier.fillMaxWidth().padding(top = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            ComponentDetailMhs(judul = "NIM", isinya = mahasiswa.nim)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Nama", isinya = mahasiswa.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Alamat", isinya = mahasiswa.alamat)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Jenis Kelamin", isinya = mahasiswa.jenisKelamin)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Kelas", isinya = mahasiswa.kelas)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Angkatan", isinya = mahasiswa.angkatan)
        }
    }
}

@Composable
fun ComponentDetailMhs( // untuk menampilkan judul dan isi data mahasiswa
    modifier: Modifier = Modifier,
    judul:String,
    isinya:String
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmationDialog( // untuk dialog konfirmasi penghapusan data mahasiswa
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = { },
        title = { Text("Delete Data")},
        text = { Text("Apakah anda yakin ingin menghapus data?")},
        dismissButton = {
            TextButton(onClick = { onDeleteCancel() }) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = { onDeleteConfirm() }) {
                Text(text = "Yes")
            }
        }
    )
}