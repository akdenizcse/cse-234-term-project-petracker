package com.example.petracker.presentation.pet_details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.petracker.domain.model.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetDetailsViewModel @Inject constructor() : ViewModel() {
    private val user = FirebaseAuth.getInstance().currentUser
    private val db = FirebaseFirestore.getInstance()



    // Method to get a pet by name
    fun getPetByName(petName: String, onSuccess: (Pet?) -> Unit, onFailure: (Exception) -> Unit) {
        user?.let {
            db.collection("users").document(it.uid).collection("pets")
                .whereEqualTo("name", petName)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        onSuccess(null)
                    } else {
                        val pet = querySnapshot.documents[0].toObject(Pet::class.java)
                        onSuccess(pet)
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }
    }

    fun updatePet(pet: Pet, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        user?.let {
            db.collection("users").document(it.uid).collection("pets")
                .whereEqualTo("name", pet.name) // Find the pet by name
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val petDocument = querySnapshot.documents[0]
                        petDocument.reference
                            .set(pet)
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener { exception ->
                                onFailure(exception)
                            }
                    } else {
                        // Handle case where pet with given name doesn't exist
                        onFailure(Exception("Pet with name ${pet.name} not found"))
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure(exception)
                }
        }
    }
}