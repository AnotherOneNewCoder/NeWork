package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.databinding.FragmentNewPostBinding
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NewPostFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)

        binding.buttonDoneFragmentNewPost.setOnClickListener {
            Toast.makeText(context, "Post created", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        return binding.root
    }
}