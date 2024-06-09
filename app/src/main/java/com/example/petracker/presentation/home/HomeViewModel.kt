package com.example.petracker.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.petracker.data.AuthRepository
import com.example.petracker.domain.model.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){

    val user = FirebaseAuth.getInstance().currentUser
    val db = FirebaseFirestore.getInstance()





    fun getPets(callback: (List<Pet>)-> Unit){
        user?.let {
            db.collection("users").document(it.uid).collection("pets")
                .get()
                .addOnSuccessListener { result->
                    val pets = result.map{document -> document.toObject(Pet::class.java)}
                    callback(pets)
                }
                .addOnFailureListener {e->
                    Log.w("Firestore", "Error getting pets", e)
                    callback(emptyList())
                }
        }
    }

}