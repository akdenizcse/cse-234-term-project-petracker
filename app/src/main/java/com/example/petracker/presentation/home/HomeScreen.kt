package com.example.petracker.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.petracker.domain.model.Pet
import com.example.petracker.navigation.Screens
import com.example.petracker.presentation.common.MedRouteTopBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit
) {


    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MedRouteTopBar(
                title = "PeTracker",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                isHomePage = true,
                onLogoutClick = {

                }
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.PetEntry.route)
                },
                shape = RoundedCornerShape(8.dp),
                contentColor = Color.White,
                containerColor = Color(0xFF7AB2B2),
                modifier = Modifier.padding(
                    end = WindowInsets.safeDrawing.asPaddingValues()
                        .calculateEndPadding(LocalLayoutDirection.current)
                )
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
            }
        })

    { paddingValues ->
        HomeBody(
            viewModel = viewModel,
            contentPadding = paddingValues,
            navController = navController,
            onItemClick = onItemClick
        )
    }
}

@Composable
fun HomeBody(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navController: NavController,
    onItemClick: (String) -> Unit,
    contentPadding: PaddingValues,
) {

    var pets by remember {
        mutableStateOf<List<Pet>>(emptyList())
    }

    LaunchedEffect(Unit) {
        viewModel.getPets { pets = it }
    }

    if (pets.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "You did not add any pets!\nClick + to add pets!")
        }
    }
    else{
        PetList(onItemClick = onItemClick, pets = pets, contentPadding = contentPadding )
    }
}

@Composable
fun PetList(
    modifier: Modifier = Modifier,
    onItemClick: (String) ->Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pets: List<Pet>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        items(pets) { pet ->
            PetListItem(
                pet = pet,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onItemClick(pet.name) }
            )
        }
    }
}

@Composable
fun PetListItem(
    modifier: Modifier = Modifier,
    pet: Pet,

    ) {

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .height(120.dp)
            .border(
                BorderStroke(1.dp, color = Color(0xFF7AB2B2)),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(2.5f)) {
                    Text(
                        text = "Name: " + pet.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF7AB2B2),
                            fontSize = 16.sp
                        ),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Age: " + pet.age,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(
                                0xFF7AB2B2
                            ),
                            fontSize = 16.sp
                        )
                    )
                }

            }
        }
    }

}