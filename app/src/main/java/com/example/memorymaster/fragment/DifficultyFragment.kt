package com.example.memorymaster.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memorymaster.activity.GameActivity
import com.example.memorymaster.databinding.FragmentDifficultyBinding

class DifficultyFragment(private val multi:Boolean) : Fragment() {

    private var _binding: FragmentDifficultyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentDifficultyBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.buttonPlayButton.setOnClickListener {
            playGame(getDifficulty())
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDifficulty(): Int {
        return if (binding.radioButtonEasyRadioButton.isChecked) {
            1
        } else if (binding.radioButtonNormalRadioButton.isChecked) {
            2
        } else {
            3
        }
    }

    private fun playGame(difficulty:Int) {
        val gamePage = Intent(activity, GameActivity::class.java)
        gamePage.putExtra("difficulty", difficulty)
        gamePage.putExtra("multi", multi)
        requireContext().startActivity(gamePage)
    }


}