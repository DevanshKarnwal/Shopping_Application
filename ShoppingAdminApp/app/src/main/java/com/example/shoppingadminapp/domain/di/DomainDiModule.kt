package com.example.shoppingadminapp.domain.di

import com.example.shoppingadminapp.data.repoImpl.RepoImpl
import com.example.shoppingadminapp.domain.repo.Repo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainDiModule {



    @Provides
    fun provideRepo(
        fireStore: FirebaseFirestore,
        storage: FirebaseStorage
    ) : Repo {
        return RepoImpl(
            fireStore = fireStore,
            storage = storage
        )
    }

}