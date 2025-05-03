package com.example.shoppingapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.domain.models.UserDataModels
import com.example.shoppingapp.presentation.viewModel.MyViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpUi(viewModel: MyViewModel = hiltViewModel()) {

    val createUserState = viewModel.createUserState.collectAsState()
    val context = LocalContext.current

    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val userEmail = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Up")

        Spacer(modifier = Modifier.height(8.dp))
        when{
            createUserState.value.isLoaded == true ->{
                CircularProgressIndicator()
            }
            createUserState.value.isError != null ->{
                Text(text = createUserState.value.isError.toString())
            }
            createUserState.value.isSuccessful != null -> {
                Toast.makeText(context, "User Created", Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            singleLine = true,
            label = { Text("First Name") },
            placeholder = { Text("First Name") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
        )
        OutlinedTextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            singleLine = true,
            label = { Text("Last Name") },
            placeholder = { Text("Last Name") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
        )
        OutlinedTextField(
            value = userEmail.value,
            onValueChange = { userEmail.value = it },
            singleLine = true,
            label = { Text("Email") },
            placeholder = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
        )
        OutlinedTextField(
            value = userPassword.value,
            onValueChange = { userPassword.value = it },
            singleLine = true,
            label = { Text("Password") },
            placeholder = { Text("Password") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
        )
        OutlinedTextField(
            value = confirmPassword.value,
            onValueChange = { confirmPassword.value = it },
            singleLine = true,
            label = { Text("Confirm Password") },
            placeholder = { Text("Confirm Password") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Exit) }),
        )

        Button(
            onClick = {
                val data = UserDataModels(
                    firstName = firstName.value,
                    lastName = lastName.value,
                    email = userEmail.value,
                    password = userPassword.value
                )
                viewModel.createUser(data)
                firstName.value = ""
                lastName.value = ""
                userEmail.value = ""
                userPassword.value = ""
                confirmPassword.value = ""
            },
            enabled = firstName.value.isNotBlank() &&
                    lastName.value.isNotBlank() &&
                    userEmail.value.isNotBlank() &&
                    userPassword.value.isNotBlank() &&
                    confirmPassword.value.isNotBlank() &&
                    userPassword.value == confirmPassword.value
        ) {
            Text("Sign Up")
        }


    }

}