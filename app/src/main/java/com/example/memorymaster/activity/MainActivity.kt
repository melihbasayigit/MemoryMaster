package com.example.memorymaster.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.memorymaster.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        binding.buttonLoginButton.setOnClickListener {
            login(
                binding.editTextTextEmailAddress.text.toString().trim { it <= ' ' },
                binding.editTextTextPassword.text.toString()
            )
        }
        binding.buttonRegisterButton.setOnClickListener {
            register(
                binding.editTextTextEmailAddress.text.toString().trim { it <= ' ' },
                binding.editTextTextPassword.text.toString()
            )
        }
        binding.textViewForgotPassword.setOnClickListener {
            resetPassword(binding.editTextTextEmailAddress.text.toString().trim { it <= ' ' })
        }

    }

    private fun switchMenuActivity() {
        val i = Intent(this@MainActivity, MenuActivity::class.java)
        i.putExtra("email", auth.currentUser?.email)
        i.putExtra("uid", auth.currentUser?.uid)
        startActivity(i)
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this@MainActivity,
                    "You logged in successfully",
                    Toast.LENGTH_LONG
                ).show()
                switchMenuActivity()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Error occurred your email address or password is wrong. Maybe you have not account yet",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity,
                        "Your account was created successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    switchMenuActivity()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Something error maybe your account already exist",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    this@MainActivity,
                    "Email sent successfully to reset password!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    task.exception!!.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}