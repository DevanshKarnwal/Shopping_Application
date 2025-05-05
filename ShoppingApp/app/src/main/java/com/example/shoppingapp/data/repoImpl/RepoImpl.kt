package com.example.shoppingapp.data.repoImpl

import android.util.Log
import com.example.shoppingapp.common.ADDTOWISHLIST_PATH
import com.example.shoppingapp.common.CATEGORY_PATH
import com.example.shoppingapp.common.PRODUCT_PATH
import com.example.shoppingapp.common.ResultState
import com.example.shoppingapp.common.USER_FAV
import com.example.shoppingapp.common.USER_PATH
import com.example.shoppingapp.domain.models.CategoryDataModels
import com.example.shoppingapp.domain.models.FavDataModel
import com.example.shoppingapp.domain.models.ProductDataModel
import com.example.shoppingapp.domain.models.UserDataModels
import com.example.shoppingapp.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFireStore: FirebaseFirestore
) : Repo {
    override fun registerUserWithEmailAndPassword(userData: UserDataModels): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            try {

                firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password)
                    .addOnSuccessListener {
                        val userId = it.user?.uid
                        firebaseFireStore.collection(USER_PATH).document(it.user?.uid.toString())
                            .set(userData).addOnSuccessListener {
                                trySend(ResultState.Success("User created successfully"))
                            }.addOnFailureListener {
                                trySend(ResultState.Error(it.message.toString()))
                            }
                    }.addOnFailureListener {
                        trySend(ResultState.Error(it.message.toString()))
                    }


            } catch (e: Exception) {
                trySend(ResultState.Error(e.message.toString()))

            }
            awaitClose {
                close()
            }
        }

    override fun loginWithEmailAndPassword(userData: UserDataModels): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            try {
                firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password)
                    .addOnSuccessListener {
                        trySend(ResultState.Success("User logged Successfully"))
                    }.addOnFailureListener {
                        trySend(ResultState.Error(it.message.toString()))
                    }

            } catch (e: Exception) {
                trySend(ResultState.Error(e.message.toString()))
            }
            awaitClose {
                close()
            }

        }

    override fun getCategory(): Flow<ResultState<List<CategoryDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        try {
            firebaseFireStore.collection(CATEGORY_PATH).get()
                .addOnSuccessListener { querySnapshot ->
                    val category = querySnapshot.documents.mapNotNull {
                        it.toObject(CategoryDataModels::class.java)
                    }

                    trySend(ResultState.Success(category))

                }.addOnFailureListener {
                    trySend(ResultState.Error(it.message.toString()))
                }

        } catch (e: Exception) {
            trySend(ResultState.Error(e.message.toString()))
        }
        awaitClose {
            close()
        }

    }

    override fun getProducts(): Flow<ResultState<List<ProductDataModel>>> = callbackFlow {
        Log.d("TAG Product", "getProducts: repoImpl")
        try {

            trySend(ResultState.Loading)
            firebaseFireStore.collection(PRODUCT_PATH).get().addOnSuccessListener {
                val product = it.documents.mapNotNull {
                    it.toObject(ProductDataModel::class.java)?.apply {
                        productId = it.id
                    }
                }
                trySend(ResultState.Success(product))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }

        } catch (e: Exception) {
            trySend(ResultState.Error(e.message.toString()))
        }
        awaitClose {
            close()
        }
    }

    override fun getProductById(productId: String): Flow<ResultState<ProductDataModel>> =
        callbackFlow {
            try {
                trySend(ResultState.Loading)
                firebaseFireStore.collection(PRODUCT_PATH).document(productId).get()
                    .addOnSuccessListener {
                        val product = it.toObject(ProductDataModel::class.java)
                        trySend(ResultState.Success(product!!))
                    }.addOnFailureListener {
                        trySend(ResultState.Error(it.message.toString()))

                    }
            } catch (e: Exception) {
                trySend(ResultState.Error(e.message.toString()))
            }
            awaitClose {
                close()

            }
        }

    override fun getProductByCategory(categoryName: String): Flow<ResultState<List<ProductDataModel>>> =
        callbackFlow {
            try {
                trySend(ResultState.Loading)
                firebaseFireStore.collection(PRODUCT_PATH).whereEqualTo("category", categoryName)
                    .get().addOnSuccessListener {
                        val product = it.documents.mapNotNull {
                            it.toObject(ProductDataModel::class.java)?.apply {
                                productId = it.id
                            }
                        }
                        trySend(ResultState.Success(product))
                    }.addOnFailureListener {
                        trySend(ResultState.Error(it.message.toString()))
                    }
            } catch (e: Exception) {
                trySend(ResultState.Error(e.message.toString()))
            }
            awaitClose {
                close()
            }
        }

    override fun addToWishList(favData: FavDataModel): Flow<ResultState<String>> = callbackFlow {
        try {
            trySend(ResultState.Loading)
            firebaseFireStore.collection(ADDTOWISHLIST_PATH)
                .document(firebaseAuth.currentUser!!.uid).collection(USER_FAV).add(favData)
                .addOnSuccessListener {
                    trySend(ResultState.Success("Added to wishlist"))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.message.toString()))
                }
        } catch (e: Exception) {
            trySend(ResultState.Error(e.message.toString()))
        }
        awaitClose {
            close()
        }
    }


}