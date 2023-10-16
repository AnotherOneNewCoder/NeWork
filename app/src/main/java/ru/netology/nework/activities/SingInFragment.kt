package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nework.R
import ru.netology.nework.databinding.FragmentSingInBinding

class SingInFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSingInBinding.inflate(layoutInflater)


        binding.buttonSingInFragmentSingIn.setOnClickListener {
            findNavController().navigate(R.id.nav_main)
        }
        binding.buttonSingUnFragmentSingIn.setOnClickListener {
            findNavController().navigate(R.id.singUpFragment)
        }

        return binding.root
    }
}