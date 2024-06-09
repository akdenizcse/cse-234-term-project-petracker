package com.example.petracker.presentation.pet_entry

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.petracker.domain.model.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetEntryViewModel @Inject constructor() : ViewModel() {
    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()
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

}