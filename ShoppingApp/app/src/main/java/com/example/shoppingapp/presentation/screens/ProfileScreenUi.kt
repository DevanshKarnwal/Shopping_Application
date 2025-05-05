package com.example.shoppingapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.shoppingapp.domain.models.UserDataModels
import com.example.shoppingapp.presentation.nav.SubNavigation
import com.example.shoppingapp.presentation.viewModel.MyViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenUi(viewModel: MyViewModel = hiltViewModel(), navController: NavController,firebaseAuth: FirebaseAuth) {

    val coroutineScope = rememberCoroutineScope()
    val getUserState = viewModel.getUserState.collectAsState()
    val updateUserDataState = viewModel.updateUserDataState.collectAsState()
    val updateUserProfileImageState = viewModel.updateUserProfileImageState.collectAsState()
    val isEditing = remember { mutableStateOf(false) }

    val imageUri = remember { mutableStateOf("") }

    val firstName = remember { mutableStateOf(getUserState.value.isSuccessful?.firstName ?: "") }
    val lastName = remember { mutableStateOf(getUserState.value.isSuccessful?.lastName ?: "") }
    val imageUrl =
        remember { mutableStateOf(getUserState.value.isSuccessful?.profilePicture ?: "") }
    val email = remember { mutableStateOf(getUserState.value.isSuccessful?.email ?: "") }
    val phoneNumber =
        remember { mutableStateOf(getUserState.value.isSuccessful?.phoneNumber ?: "") }
    val password = remember { mutableStateOf(getUserState.value.isSuccessful?.password ?: "") }
    val address = remember { mutableStateOf(getUserState.value.isSuccessful?.address ?: "") }
    val focus = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            viewModel.getUser()
        }
    }

    when{
        updateUserDataState.value.isLoaded -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        updateUserDataState.value.isError != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = updateUserDataState.value.isError.toString())
            }
        }
        updateUserDataState.value.isSuccessful != null -> {
            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
        }

    }

    when {
        getUserState.value.isLoaded -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        getUserState.value.isError != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = getUserState.value.isError.toString())
            }
        }

        getUserState.value.isSuccessful != null -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Profile") }
                    )
                }
            ) { innerPadding ->
                LaunchedEffect(key1 = getUserState.value.isSuccessful) {
                    CoroutineScope(Dispatchers.IO).launch {
                        firstName.value = getUserState.value.isSuccessful?.firstName ?: ""
                        lastName.value = getUserState.value.isSuccessful?.lastName ?: ""
                        imageUrl.value = getUserState.value.isSuccessful?.profilePicture ?: ""
                        email.value = getUserState.value.isSuccessful?.email ?: ""
                        phoneNumber.value = getUserState.value.isSuccessful?.phoneNumber ?: ""
                        password.value = getUserState.value.isSuccessful?.password ?: ""
                        address.value = getUserState.value.isSuccessful?.address ?: ""
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = getUserState.value.isSuccessful?.profilePicture,
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(20.dp)
                            .size(120.dp)
                    )

                    OutlinedTextField(
                        value = firstName.value,
                        onValueChange = { firstName.value = it },
                        label = { Text("First Name") },
                        placeholder = { Text("First Name") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions { focus.moveFocus(FocusDirection.Down) },
                        readOnly = !isEditing.value
                    )
                    OutlinedTextField(
                        value = lastName.value,
                        onValueChange = { lastName.value = it },
                        label = { Text("Last Name") },
                        placeholder = { Text("Last Name") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions { focus.moveFocus(FocusDirection.Down) },
                        readOnly = !isEditing.value
                    )
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Email") },
                        placeholder = { Text("Email") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions { focus.moveFocus(FocusDirection.Down) },
                        readOnly = !isEditing.value
                    )
                    OutlinedTextField(
                        value = phoneNumber.value,
                        onValueChange = { phoneNumber.value = it },
                        label = { Text("Phone Number") },
                        placeholder = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions { focus.moveFocus(FocusDirection.Down) },
                        readOnly = !isEditing.value
                    )
                    OutlinedTextField(
                        value = address.value,
                        onValueChange = { address.value = it },
                        label = { Text("Address") },
                        placeholder = { Text("Address") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions { focus.moveFocus(FocusDirection.Down) },
                        readOnly = !isEditing.value
                    )
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text("Password") },
                        placeholder = { Text("Password") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions { focus.moveFocus(FocusDirection.Down) },
                        readOnly = !isEditing.value
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        if(isEditing.value == false){
                            Button(onClick = {
                                isEditing.value = true
                            }) {
                                Text(text = "Edit")
                            }
                        }
                        else{
                            Button(onClick = {
                                val data = UserDataModels(
                                    firstName = firstName.value,
                                    lastName = lastName.value,
                                    email = email.value,
                                    phoneNumber = phoneNumber.value,
                                    address = address.value,
                                    password = password.value,
                                    profilePicture = imageUrl.value
                                )
                                coroutineScope.launch(Dispatchers.IO) {
                                    viewModel.updateUser(data)
                                }
                                isEditing.value = false
                            }) {
                                Text(text = "Save")
                            }
                        }
                        Spacer(modifier = Modifier.width(40.dp))
                        Button(onClick = {
                            firebaseAuth.signOut()
                            navController.navigate(SubNavigation.LoginSignUpScreenRoutes){
                                popUpTo(SubNavigation.HomeScreenRoutes){
                                    inclusive = true
                                }
                            }
                        }){
                            Text(text = "Logout")
                        }
                    }



                }
            }

        }

    }

}