package com.example.memorymaster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.get
import com.example.memorymaster.databinding.FragmentDifficultyBinding

class DifficultyFragment : Fragment() {

    private var _binding: FragmentDifficultyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        requireContext().startActivity(gamePage)
    }


}