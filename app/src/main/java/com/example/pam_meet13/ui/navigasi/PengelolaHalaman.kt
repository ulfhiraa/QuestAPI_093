package com.example.pam_meet13.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pam_meet13.ui.view.DestinasiEntry
import com.example.pam_meet13.ui.view.DestinasiHome
import com.example.pam_meet13.ui.view.EntryMhsScreen
import com.example.pam_meet13.ui.view.HomeScreen

// fungsi PengelolaHalaman untuk mengatur navigasi antara layar Home
// dan Entry Mahasiswa dengan rute dan transisi yang sesuai

// untuk mengelola navigasi antara layar Home dan layar Entry Mahasiswa
@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost( // untuk memulai navigasi dari HomeScreen dan menyediakan rute ke EntryMhsScreen
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route)},
                onDetailClick = {

                }
            )
        }

        composable(DestinasiEntry.route){
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){
                        inclusive = true
                    }
                }
            })
        }
    }
}