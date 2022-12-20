package com.example.memorymaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.memorymaster.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private var score:Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.playSingleplayerButton.setOnClickListener { view ->
            difficultyFragment(view)
        }
        binding.textViewUserId.text = intent.getStringExtra("uid")
        binding.textViewUserEmail.text = intent.getStringExtra("email")
        score = intent.getFloatExtra("score", -10000.0f)
        if (score != -10000.0f) {
            Toast.makeText(this, "Game over your score is: $score",Toast.LENGTH_LONG).show()
            finish()
        }

    }

    private fun difficultyFragment(view: View) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = DifficultyFragment()
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit()
    }


}