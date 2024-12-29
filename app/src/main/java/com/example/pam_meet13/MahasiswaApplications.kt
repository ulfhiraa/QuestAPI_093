package com.example.pam_meet13

import android.app.Application
import com.example.pam_meet13.dependenciesinjection.AppContainer
import com.example.pam_meet13.dependenciesinjection.MahasiswaContainer

//   untuk inisialisasi mengatur komponen yang akan dipakai di seluruh aplikasi
class MahasiswaApplications:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container= MahasiswaContainer()
    }
}