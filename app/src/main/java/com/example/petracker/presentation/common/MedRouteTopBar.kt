package com.example.petracker.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedRouteTopBar(
    modifier: Modifier = Modifier,
    title: String,
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    isHomePage: Boolean = false,

) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.displayMedium) },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF7AB2B2),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (isHomePage) {
                IconButton(onClick = onLogoutClick) {
                    Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Logout")
                }
            }
        }

    )
}