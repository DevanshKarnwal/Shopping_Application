package com.example.shoppingadminapp.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideFireStoreInstance() = FirebaseFirestore.getInstance()

    @Provides
    fun providesFirebaseStorageInstance() = FirebaseStorage.getInstance()



}