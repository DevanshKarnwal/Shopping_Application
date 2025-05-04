package com.example.shoppingapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shoppingapp.domain.models.UserDataModels
import com.example.shoppingapp.presentation.nav.Routes
import com.example.shoppingapp.presentation.viewModel.MyViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LogInScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavHostController) {
    val userEmail = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }
    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current
    val loginState = viewModel.loginUserState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFFF57C7C),
                radius = 200f,
                center = Offset(x = size.width, y = 0f)
            )
            drawCircle(
                color = Color(0xFFF57C7C),
                radius = 200f,
                center = Offset(x = 0f, y = size.height)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when{
                loginState.value.isLoaded -> {
                    CircularProgressIndicator()
                }
                loginState.value.isError != null -> {
                    Text(text = loginState.value.isError.toString())
                }
                loginState.value.isSuccessful != null -> {
                    Toast.makeText(context, loginState.value.isSuccessful, Toast.LENGTH_SHORT).show()
                    navController.navigate(Routes.HomeScreenRoute)
                }
            }

            Text(
                text = "Login",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = userEmail.value,
                onValueChange = { userEmail.value = it },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF57C7C),
                    unfocusedBorderColor = Color(0xFFF57C7C)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = userPassword.value,
                onValueChange = { userPassword.value = it },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    localFocusManager.clearFocus()
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF57C7C),
                    unfocusedBorderColor = Color(0xFFF57C7C)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Forgot Password?",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val data = UserDataModels(email = userEmail.value, password = userPassword.value)
                    viewModel.loginUser(data)
                    userEmail.value = ""
                    userPassword.value = ""
                },
                enabled = userEmail.value.isNotBlank() && userPassword.value.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF57C7C))
            ) {
                Text("Login", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text("Don’t have an account? ", color = Color.Gray)
                Text(
                    text = "Sign Up",
                    color = Color(0xFFF57C7C),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        navController.navigate(Routes.SignUpScreenRoute)
                    }
                )
            }

        }
    }
}
