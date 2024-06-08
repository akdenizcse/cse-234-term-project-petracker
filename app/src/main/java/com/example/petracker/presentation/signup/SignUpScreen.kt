package com.example.petracker.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petracker.presentation.common.MedRouteTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        MedRouteTopBar(
            title = "Sign Up",
            canNavigateBack = true,
            scrollBehavior = scrollBehavior,
            navigateUp = { navController.navigateUp() }
        )
    }) { paddingValues ->
        SignUpBody(
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                )
                .fillMaxSize(),
            navController = navController
        )
    }

}

@Composable
fun SignUpBody(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state by viewModel.signUpState.collectAsState(initial = null)

    LaunchedEffect(state) {
        if(!state?.isSuccess.isNullOrEmpty()){
            navController.navigateUp()
        }else if(!state?.isError.isNullOrEmpty()){
            Toast.makeText(context, state?.isError, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
        Spacer(modifier = Modifier.height(18.dp))
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
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(KeyboardActions.Default.onNext),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(18.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7AB2B2),
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                scope.launch {
                    viewModel.createUser(email, password)
                }
            },
            enabled = email.isNotEmpty() && password.isNotEmpty()

        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        val annotatedString = buildAnnotatedString {
            append("Do you have an account? ")
            pushStringAnnotation("Login", "Login")
            withStyle(
                style = SpanStyle(
                    color = Color(0xFF7AB2B2),
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                )
            ) {
                append("Login!")
            }
            pop()
        }
        ClickableText(
            text = annotatedString, onClick = { offset ->
                annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.also { span ->
                    if (span.item == "Login") {
                        navController.navigateUp()
                    }
                }
            }, style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xA67AB2B2), fontSize = 18.sp
            )
        )
    }
}