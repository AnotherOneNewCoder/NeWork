package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nework.R
import ru.netology.nework.databinding.FragmentSingUpBinding

class SingUpFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSingUpBinding.inflate(layoutInflater)


        binding.buttonSingUpFragmentSingUp.setOnClickListener {
            Toast.makeText(context, "Registration is done!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.nav_main)
        }

        return binding.root
    }
}
