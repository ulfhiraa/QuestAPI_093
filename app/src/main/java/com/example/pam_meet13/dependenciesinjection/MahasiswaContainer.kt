package com.example.pam_meet13.dependenciesinjection

import com.example.pam_meet13.repository.MahasiswaRepository

// interface AppContainer untuk menyediakan akses ke MahasiswaRepository.
interface AppContainer{
    val mahasiswaRepository: MahasiswaRepository
}