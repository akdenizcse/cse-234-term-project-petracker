package com.example.petracker.presentation.login

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petracker.navigation.Screens
import com.example.petracker.presentation.common.MedRouteTopBar
import com.example.petracker.presentation.signup.SignUpBody
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        MedRouteTopBar(
            title = "PeTracker",
            canNavigateBack = false,
            scrollBehavior = scrollBehavior,
        )
    }) { paddingValues ->
        LoginBody(
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                    end = paddingValues.calculateEndPadding(layoutDirection = LocalLayoutDirection.current),
                    top = paddingValues.calculateTopPadding()
                )
                .fillMaxSize(),
            navController = navController
        )
    }
}

@Composable
fun LoginBody(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state by viewModel.signInState.collectAsState(initial = null)

    LaunchedEffect(state) {
        state?.let {
            if (it.isSuccess?.isNotEmpty() == true) {
                navController.navigate(Screens.HomeScreen.route) // Replace with your home screen route
            } else if (it.isError?.isNotEmpty() == true) {
                Toast.makeText(context, it.isError, Toast.LENGTH_LONG).show()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEEF7FF),
                unfocusedContainerColor = Color(0xFFEEF7FF),
                disabledContainerColor = Color(0xFFEEF7FF),
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = Color(0xA67AB2B2)
                )
            },
            shape = RoundedCornerShape(8.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2)),
            label = {
                Text(
                    text = "Enter Email",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(KeyboardActions.Default.onNext)
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEEF7FF),
                unfocusedContainerColor = Color(0xFFEEF7FF),
                disabledContainerColor = Color(0xFFEEF7FF),
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color(0xA67AB2B2)
                )
            },
            shape = RoundedCornerShape(8.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2)),
            label = {
                Text(
                    text = "Enter Password",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = KeyboardActions.Default.onDone),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(30.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7AB2B2),
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                scope.launch {
                    viewModel.loginUser(email, password)
                }
            },
            enabled = email.isNotEmpty() && password.isNotEmpty(),
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (state?.isLoading == true) {
                CircularProgressIndicator()
            }
        }

        val annotatedString = buildAnnotatedString {
            append("If you don't have an account ")
            pushStringAnnotation("Sign Up", "Sign Up")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF7AB2B2),
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("Sign Up")
            }
            pop()
            append(" here!")
        }
        ClickableText(text = annotatedString, onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    if (span.item == "Sign Up") {
                        navController.navigate(route = Screens.SignUpScreen.route)
                    }
                }
        }, style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2)))
    }
}





























