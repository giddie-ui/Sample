package com.example.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sample.ui.theme.SampleTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        setContent {
            SampleTheme {
                Surface {
                    LoginPage { email, password ->
                        signIn(email, password)
                    }
                }
            }
        }
    }

    private fun signIn(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    setContent {
                        SampleTheme {
                            Surface {
                                HomePage()
                            }
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(onSignIn: (String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign In",
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        Button(
            onClick = { onSignIn(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In")
        }
    }
}

@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Welcome User!",
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Your Farming App",
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                CardItem(
                    title = "Breed Selection",
                    description = "Find the perfect breed for your cattle based on various factors like climate suitability, milk production,age, etc.",
                    onClick = { /* Navigate to breed selection screen */ }
                )
                CardItem(
                    title = "calf routine",
                    description = "Specify various calf feeds and supplements.",
                    onClick = { /* Navigate to market trends screen */ }
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                CardItem(
                    title = "Health & Nutrition",
                    description = "Access resources and information on maintaining the health and nutrition of your cattle.",
                    onClick = { /* Navigate to health & nutrition screen */ }
                )
                CardItem(
                    title = "pregnacy ",
                    description = "basic information supplements during all the trimesters.",
                    onClick = { /* Navigate to community forum screen */ }
                )
            }
        }
    }
}

@Composable
fun CardItem(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = description,
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    SampleTheme {
        Surface {
            LoginPage { _, _ -> }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    SampleTheme {
        Surface {
            HomePage()
        }
    }
}
