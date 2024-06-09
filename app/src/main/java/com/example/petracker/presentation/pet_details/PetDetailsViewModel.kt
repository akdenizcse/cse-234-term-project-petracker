package com.example.petracker.presentation.pet_details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.petracker.domain.model.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetDetailsViewModel @Inject constructor() : ViewModel() {
    private val user = FirebaseAuth.getInstance().currentUser
    private val db = FirebaseFirestore.getInstance()

    // Method to add a pet
    fun addPet(pet: Pet) {
        user?.let {
            db.collection("users").document(it.uid).collection("pets")
                .add(pet)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firestore", "Pet added with id ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding pet", e)
                }
        }
    }

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
}