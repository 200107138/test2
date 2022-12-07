package com.example.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.ActivitySignInBinding
import com.example.test.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        binding.button.setOnClickListener {
            registerUser()
        }
    }
    private fun registerUser(){
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isSuccessful){
                val databaseRef = database.reference.child("users").child(auth.currentUser!!.uid)
                val user = Users("", listOf(0), listOf(0), listOf(0))
databaseRef.setValue(user).addOnCompleteListener{
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
}

            }
            else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

}





