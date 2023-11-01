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
import ru.netology.nework.R
import ru.netology.nework.databinding.FragmentImageAttachBinding
import ru.netology.nework.handler.loadImage

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ImageAttachFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentImageAttachBinding.inflate(layoutInflater, container, false)

        binding.apply {
            image.visibility = View.GONE
            var url = arguments?.getString("attach_img")
            if (url != null) {
                image.loadImage(url)
                image.visibility = View.VISIBLE
            } else {
                Toast.makeText(context, getString(R.string.error_loading), Toast.LENGTH_SHORT).show()
            }
            image.setOnClickListener {
                findNavController().popBackStack()
            }
        }



        return binding.root
    }
}