package com.example.shoppingapp.domain.di

import com.example.shoppingapp.data.repoImpl.RepoImpl
import com.example.shoppingapp.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object  domainDiMoudule{

    @Provides
    fun getRepo(
        firebaseAuth: FirebaseAuth,
        firebaseFireStore: FirebaseFirestore,
        firebaseStorage: FirebaseStorage
    ) : Repo{
        return RepoImpl(
            firebaseAuth = firebaseAuth,
            firebaseFireStore = firebaseFireStore,
            firebaseStorage = firebaseStorage
        )
    }

}