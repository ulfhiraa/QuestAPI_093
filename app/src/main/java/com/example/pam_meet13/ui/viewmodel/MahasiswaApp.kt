package com.example.pam_meet13.ui.viewmodel

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.pam_meet13.ui.navigasi.PengelolaHalaman

// sebagai kerangka utama aplikasi.
// untuk menampilkan struktur dasar aplikasi dengan scrollable layout dan menampilkan PengelolaHalaman
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MahasiswaApp(){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        // topBar = { TopAppBar(scrollBehavior = scrollBehavior) }
    ){
        Surface(// untuk memastikan tata letak penuh aplikasi
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            PengelolaHalaman()
        }
    }
}