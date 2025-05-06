package com.example.shoppingadminapp.domain.UseCase

import android.net.Uri
import com.example.shoppingadminapp.domain.repo.Repo
import javax.inject.Inject

class AddBannerImageUseCase @Inject constructor(private val repo: Repo) {
    suspend fun addBannerImageUseCase(photoUri: Uri) = repo.addBannerPhoto(photoUri)

}