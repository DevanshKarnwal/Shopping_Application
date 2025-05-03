package com.example.shoppingadminapp.data.repoImpl

import android.net.Uri
import com.example.shoppingadminapp.common.CATEGORY_PATH
import com.example.shoppingadminapp.common.PRODUCT_PATH
import com.example.shoppingadminapp.common.ResultState
import com.example.shoppingadminapp.domain.models.CategoryModels
import com.example.shoppingadminapp.domain.models.ProductModels
import com.example.shoppingadminapp.domain.repo.Repo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : Repo {
    override suspend fun addCategory(category: CategoryModels): Flow<ResultState<String>> = callbackFlow {
                trySend(ResultState.Loading)
                try {
                    fireStore.collection(CATEGORY_PATH).add(category).addOnSuccessListener {
                        trySend(ResultState.Success("Category Added Successfully"))
                    }.addOnFailureListener {
                        trySend(ResultState.Error(it.message.toString()))
                    }
                }
                catch (e: Exception){
                    trySend(ResultState.Error(e.message.toString()))
                }
                awaitClose{
                    close()
                }
        }

    override suspend fun getAllCategories(): Flow<ResultState<List<CategoryModels>>> = callbackFlow  {
        trySend(ResultState.Loading)
        try {
            fireStore.collection(CATEGORY_PATH).get().addOnSuccessListener {
                val categories = it.documents.mapNotNull { doc ->
                    doc.toObject(CategoryModels::class.java)
                }
                trySend(ResultState.Success(categories))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        }
        catch (e: Exception){
            trySend(ResultState.Error(e.message.toString()))
        }
        awaitClose{
            close()
        }
    }

    override suspend fun addProduct(product: ProductModels): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        try {
            fireStore.collection(PRODUCT_PATH).add(product).addOnSuccessListener {
                trySend(ResultState.Success("Product Added Successfully"))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        }
        catch (e: Exception){
            trySend(ResultState.Error(e.message.toString()))
        }
        awaitClose{
            close()
        }
    }

    override suspend fun addProductPhoto(photoUri: Uri): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        try {
            storage.reference.child("products/${System.currentTimeMillis()}").putFile(
                photoUri ?: Uri.EMPTY
            ).addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener {
                            trySend(ResultState.Success(it.toString()))
                    }.addOnFailureListener {
                        trySend(ResultState.Error(it.message.toString()))
                    }
            }
        }catch (e: Exception){
            trySend(ResultState.Error(e.message.toString()))
        }
        awaitClose{
            close()
        }
    }



}