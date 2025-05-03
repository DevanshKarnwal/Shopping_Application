package com.example.shoppingapp.data.repoImpl

import com.example.shoppingapp.common.ResultState
import com.example.shoppingapp.common.USER_PATH
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
                            trySend(ResultState.Success("User created with UID: $userId"))
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

    override fun loginWithEmailAndPassword(userData: UserDataModels): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        try {
             firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password).addOnSuccessListener {
                 trySend(ResultState.Success("User logged Successfully"))
             }.addOnFailureListener {
                 trySend(ResultState.Error(it.message.toString()))
             }

        }catch (e : Exception){
            trySend(ResultState.Error(e.message.toString()))
        }
        awaitClose {
            close()
        }

    }

}