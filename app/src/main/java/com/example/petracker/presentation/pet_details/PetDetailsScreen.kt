package com.example.petracker.presentation.pet_details

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavController
import com.example.petracker.domain.model.Pet
import com.example.petracker.presentation.common.MedRouteTopBar
import com.example.petracker.presentation.pet_entry.PetEntryBody
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailsScreen(
    modifier: Modifier = Modifier,
    petName: String,
    navController: NavController

) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        MedRouteTopBar(
            title = petName,
            canNavigateBack = true,
            scrollBehavior = scrollBehavior,
            navigateUp = { navController.navigateUp() }
        )
    }) { paddingValues ->
        PetDetailsBody(
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                    end = paddingValues.run { calculateEndPadding(layoutDirection = LocalLayoutDirection.current) },
                    top = paddingValues.calculateTopPadding()
                )
                .fillMaxSize(),
            navController = navController
        )
    }
}

@Composable
fun PetDetailsBody(modifier: Modifier = Modifier, navController: NavController) {

}