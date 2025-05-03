package com.example.shoppingadminapp.domain.UseCase

import android.net.Uri
import com.example.shoppingadminapp.domain.repo.Repo
import javax.inject.Inject

class AddProductPhotoUseCase @Inject constructor(private val repo: Repo) {
    suspend fun addProductPhotoUseCase(photoUri: Uri) = repo.addProductPhoto(photoUri)
}