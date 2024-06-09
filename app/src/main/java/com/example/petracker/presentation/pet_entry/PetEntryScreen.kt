package com.example.petracker.presentation.pet_entry

import android.util.Log
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetEntryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PetEntryViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        MedRouteTopBar(
            title = "Add Pet",
            canNavigateBack = true,
            scrollBehavior = scrollBehavior,
            navigateUp = { navController.navigateUp() }
        )
    }) { paddingValues ->
        PetEntryBody(
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(layoutDirection = LocalLayoutDirection.current),
                    end = paddingValues.run { calculateEndPadding(layoutDirection = LocalLayoutDirection.current) },
                    top = paddingValues.calculateTopPadding()
                )
                .fillMaxSize(),
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun PetEntryBody(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PetEntryViewModel
) {
    var name by remember {
        mutableStateOf("")
    }
    var age by remember {
        mutableStateOf("")
    }
    var hasFood by remember {
        mutableStateOf(false)
    }
    var hasWater by remember {
        mutableStateOf(false)
    }
    var isSick by remember {
        mutableStateOf(false)
    }
    var gotMedicine by remember {
        mutableStateOf(false)
    }
    var sickDays by remember {
        mutableStateOf("")
    }

    var sickDaysInt by remember {
        mutableStateOf(0)
    }
    var ageInt by remember {
        mutableStateOf(0)
    }
    var medicineGiven by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEEF7FF),
                unfocusedContainerColor = Color(0xFFEEF7FF),
                disabledContainerColor = Color(0xFFEEF7FF),
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2)),
            label = {
                Text(
                    text = "Pet Name",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = age,
            onValueChange = {
                age = it
                if (it != "")
                    ageInt = age.toInt()
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFEEF7FF),
                unfocusedContainerColor = Color(0xFFEEF7FF),
                disabledContainerColor = Color(0xFFEEF7FF),
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2)),
            label = {
                Text(
                    text = "Pet Age",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(checked = hasFood, onCheckedChange = { hasFood = it })
            Text(text = "Has Food?")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(checked = hasWater, onCheckedChange = { hasWater = it })
            Text(text = "Has Water?")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(checked = isSick, onCheckedChange = { isSick = it })
            Text(text = "Is Sick?")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isSick) {
            OutlinedTextField(
                value = sickDays,
                onValueChange = {
                    sickDays = it
                    if (it != "")
                        sickDaysInt = sickDays.toInt()
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEEF7FF),
                    unfocusedContainerColor = Color(0xFFEEF7FF),
                    disabledContainerColor = Color(0xFFEEF7FF),
                    disabledBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2)),
                label = {
                    Text(
                        text = "Sick Days",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xA67AB2B2))
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Checkbox(checked = gotMedicine, onCheckedChange = {
                    gotMedicine = it
                    medicineGiven = it
                })
                Text(text = "Got Medicine?")
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (gotMedicine && sickDaysInt >= 1 && medicineGiven) {
                sickDaysInt -= 1
                sickDays = sickDaysInt.toString()
                medicineGiven = false
            }
            if (sickDaysInt == 0) {
                Text(text = "$name is healed.")
            }
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7AB2B2),
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                viewModel.addPet(
                    Pet(
                        name = name,
                        age = ageInt,
                        hasFood = hasFood,
                        hasWater = hasWater,
                        sick = isSick,
                        gotMedicine = gotMedicine,
                        sickDays = sickDaysInt
                    )
                )
                navController.navigateUp()
            },
            enabled = name.isNotEmpty() && age.isNotEmpty(),
        ) {
            Text(
                text = "Save Pet",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7AB2B2),
                contentColor = Color.White,
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = { navController.navigateUp() },
        ) {
            Text(
                text = "Cancel",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
            )
        }
    }


}