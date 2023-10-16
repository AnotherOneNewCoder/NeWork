package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nework.databinding.FragmentNewEventBinding

class NewEventFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewEventBinding.inflate(layoutInflater, container, false)



        binding.buttonDoneFragmentNewEvent.setOnClickListener {
            Toast.makeText(context, "New event saved", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        return binding.root
    }
}