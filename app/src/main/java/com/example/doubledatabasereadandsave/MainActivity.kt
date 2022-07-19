package com.example.doubledatabasereadandsave

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.doubledatabasereadandsave.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        val loginButton = binding.btnLogin

        loginButton.setOnClickListener {
            validateData()
        }
    }

    private fun validateData(){
        val email = binding.etLoginEmail.text.toString().trim()
        val password = binding.etLoginPassword.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid email format...", Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            Toast.makeText(this, "Enter password...", Toast.LENGTH_SHORT).show()
        }
        else {
            loginUser(email, password)
        }
    }

    private fun loginUser(email : String, password : String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(Intent(this@MainActivity, DataViewActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(this, "Login failed due to ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser(){
        val user = mAuth.currentUser
        var ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(user!!.uid)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    startActivity(Intent(this@MainActivity, DataViewActivity::class.java))

                }
            }
            )

    }
}