package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nework.R
import ru.netology.nework.databinding.FragmentNewJobBinding

class NewJobFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewJobBinding.inflate(layoutInflater, container, false)







        binding.buttonSaveFragmentNewJob.setOnClickListener {
            Toast.makeText(context, R.string.saved, Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
        return binding.root
    }
}