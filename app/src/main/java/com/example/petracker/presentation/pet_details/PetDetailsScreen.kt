package com.example.petracker.presentation.pet_details

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    navController: NavController,
    viewModel: PetDetailsViewModel = hiltViewModel()

) {

    var pet by remember { mutableStateOf<Pet?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(petName) {
        viewModel.getPetByName(petName,
            onSuccess = {
                pet = it
            },
            onFailure = {
                errorMessage = it.message
            }
        )
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        MedRouteTopBar(
            title = petName,
            canNavigateBack = true,
            scrollBehavior = scrollBehavior,
            navigateUp = { navController.navigateUp() }
        )
    }) { paddingValues ->
        if (pet != null) {
            PetDetailsBody(
                modifier = Modifier
                    .padding(
                        start = paddingValues.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                        end = paddingValues.run { calculateEndPadding(layoutDirection = LocalLayoutDirection.current) },
                        top = paddingValues.calculateTopPadding()
                    )
                    .fillMaxSize(),
                navController = navController,
                viewModel = viewModel,
                pet = pet!!
            )
        } else {
            viewModel.getPetByName(
                petName,
                onSuccess = { pet = it },
                onFailure = { errorMessage = it.message })
        }
    }
}

@Composable
fun PetDetailsBody(
    modifier: Modifier = Modifier,
    navController: NavController,
    pet: Pet,
    viewModel: PetDetailsViewModel
) {
    var name by remember { mutableStateOf(pet.name) }
    var age by remember { mutableStateOf(pet.age.toString()) }
    var hasFood by remember { mutableStateOf(pet.hasFood) }
    var hasWater by remember { mutableStateOf(pet.hasWater) }
    var isSick by remember { mutableStateOf(pet.sick) }
    var gotMedicine by remember { mutableStateOf(pet.gotMedicine) }
    var sickDays by remember { mutableStateOf(pet.sickDays.toString()) }
    var isUpdated by remember {
        mutableStateOf(false)
    }
    var sickDaysInt by remember { mutableStateOf(pet.sickDays) }
    var ageInt by remember { mutableStateOf(pet.age) }
    var medicineGiven by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEEF7FF),
                unfocusedContainerColor = Color(0xFFEEF7FF),
                disabledContainerColor = Color(0xFFEEF7FF),
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = age,
            onValueChange = {
                age = it
                if (it.isNotEmpty()) ageInt = it.toInt()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEEF7FF),
                unfocusedContainerColor = Color(0xFFEEF7FF),
                disabledContainerColor = Color(0xFFEEF7FF),
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            // Other attributes for OutlinedTextField...
        )

        Spacer(modifier = Modifier.height(16.dp))

        CheckboxWithLabel(
            text = "Has Food?",
            checked = hasFood,
            onCheckedChange = { hasFood = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CheckboxWithLabel(
            text = "Has Water?",
            checked = hasWater,
            onCheckedChange = { hasWater = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CheckboxWithLabel(
            text = "Is Sick?",
            checked = isSick,
            onCheckedChange = { isSick = it }
        )
        if (isSick) {
            OutlinedTextField(
                value = sickDays,
                onValueChange = {
                    sickDays = it
                    if (it.isNotEmpty()) sickDaysInt = it.toInt()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEEF7FF),
                    unfocusedContainerColor = Color(0xFFEEF7FF),
                    disabledContainerColor = Color(0xFFEEF7FF),
                    disabledBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                // Other attributes for OutlinedTextField...
            )
            Spacer(modifier = Modifier.height(16.dp))
            CheckboxWithLabel(text = "Got Medicine?", checked = gotMedicine, onCheckedChange = {
                gotMedicine = it
                medicineGiven = true
            }, isEnabled = !medicineGiven)
            Spacer(modifier = Modifier.height(16.dp))
            if (gotMedicine && sickDaysInt >= 1 && !isUpdated) {
                sickDaysInt -= 1
                sickDays = sickDaysInt.toString()
                isUpdated = true
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val updatedPet = Pet(
                    name = name,
                    age = ageInt,
                    hasFood = hasFood,
                    hasWater = hasWater,
                    sick = isSick,
                    gotMedicine = false,
                    sickDays = sickDaysInt
                )
                isUpdated = true
                viewModel.updatePet(updatedPet,
                    onSuccess = {
                        // Handle success, maybe navigate back or show a confirmation
                    },
                    onFailure = { exception ->
                        // Handle failure, show error message or retry option
                    }
                )
            }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7AB2B2),
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = ((pet.age != ageInt) || (pet.sick != isSick) || (pet.hasFood != hasFood) || (pet.hasWater != hasWater) || (pet.gotMedicine != gotMedicine) || (pet.sickDays != sickDaysInt))
        ) {
            Text("Update Pet")
        }
    }
}

@Composable
fun CheckboxWithLabel(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isEnabled: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 8.dp),
            enabled = isEnabled, colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF7AB2B2)
            )
        )
        Text(text, style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2)))
    }
}