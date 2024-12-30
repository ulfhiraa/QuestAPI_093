package com.example.pam_meet13.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pam_meet13.ui.view.DestinasiDetail
import com.example.pam_meet13.ui.view.DestinasiEntry
import com.example.pam_meet13.ui.view.DestinasiHome
import com.example.pam_meet13.ui.view.DestinasiUpdate
import com.example.pam_meet13.ui.view.DetailView
import com.example.pam_meet13.ui.view.EntryMhsScreen
import com.example.pam_meet13.ui.view.HomeScreen
import com.example.pam_meet13.ui.view.UpdateView

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
        // HOME
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route)},
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route}/$nim")
                }
            )
        }

        // TAMBAH
        composable(DestinasiEntry.route){
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){
                        inclusive = true
                    }
                }
            })
        }

        // DETAIL
        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.NIM) {
                    type = NavType.StringType
                }
            )
        ) {
            val nim = it.arguments?.getString(DestinasiDetail.NIM)
            nim?.let { nim ->
                DetailView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$nim")
                    },
                    nim = nim,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // UPDATE
        composable(
            DestinasiUpdate.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdate.NIM) {
                    type = NavType.StringType
                }
            )
        ) {
            val nim = it.arguments?.getString(DestinasiUpdate.NIM)
            nim?.let { nim ->
                UpdateView(
                    navigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateUp =  {
                        navController.navigate(DestinasiHome.route) },
                    nim = nim
                )
            }
        }
    }
}