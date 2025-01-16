package com.example.wearcast.data.User

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Email
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
fun SignUp(
    viewModel: UserViewModel = viewModel(),
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isWoman by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F4673), // Deep blue
                        Color(0xFF42A5F5)  // Light blue
                    )
                )
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "Create an Account",
                color = Color.White,
                fontSize = 28.sp,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Username Field
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Color.LightGray) },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Username Icon", tint = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF42A5F5),
                    unfocusedBorderColor = Color.LightGray,
                    textColor = Color.White
                )
            )

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.LightGray) },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "Email Icon", tint = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF42A5F5),
                    unfocusedBorderColor = Color.LightGray,
                    textColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.LightGray) },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "Password Icon", tint = Color.White)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF42A5F5),
                    unfocusedBorderColor = Color.LightGray,
                    textColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Female", color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Checkbox(
                    checked = isWoman,
                    onCheckedChange = { isChecked ->
                        isWoman = isChecked  // When Female is checked, Male should be unchecked
                    },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF42A5F5), uncheckedColor = Color.Gray)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("Male", color = Color.White)
                Spacer(modifier = Modifier.width(6.dp))
                Checkbox(
                    checked = !isWoman,  // Male is the opposite of Female
                    onCheckedChange = { isChecked ->
                        isWoman = !isChecked  // When Male is checked, Female should be unchecked
                    },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF42A5F5), uncheckedColor = Color.Gray)
                )
            }

            // Sign Up Button
            Button(
                onClick = {
                    if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                        val user = User(
                            username = username,
                            password = password,
                            email = email,
                            isWoman = isWoman
                        )
                        viewModel.addUser(user) { success, message ->
                            if (success) {
                                onSignUpSuccess()
                            } else {
                                errorMessage = message
                            }
                        }
                    } else {
                        errorMessage = "Please fill in all fields"
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF42A5F5)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Sign Up", color = Color.White)
            }

            // Error Message
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Login Navigation
            TextButton(
                onClick = onLoginClick,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("I already have an account.", color = Color.White)
            }
        }
    }
}
