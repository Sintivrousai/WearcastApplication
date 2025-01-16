package com.example.wearcast.data.User

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Login(viewModel: UserViewModel = viewModel(), onLoginSuccess: () -> Unit, onSignUpClick: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F4673), // Deep consistent blue
                        Color(0xFF42A5F5) // Light blue
                    )
                )
            )
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "Welcome Back",
                color = Color.White,
                fontSize = 28.sp,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Username Field
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Color.White) },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Username Icon", tint = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    textColor = Color.White
                )
            )

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "Password Icon", tint = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    textColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // Login Button
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.validateUser(username, password) { success ->
                        if (success) {
                            onLoginSuccess()
                        } else {
                            errorMessage = "Invalid username or password"
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF42A5F5)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Login", color = Color.White)
            }

            // Error Message Display
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            // Sign Up Navigation Option
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = onSignUpClick, // Trigger navigation to Sign Up
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "I don't have an account.",
                    color = Color.White,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
