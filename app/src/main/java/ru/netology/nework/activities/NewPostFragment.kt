package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.R
import ru.netology.nework.databinding.FragmentNewPostBinding
import ru.netology.nework.dto.TypeAttachment
import ru.netology.nework.handler.loadImage
import ru.netology.nework.utils.AndroidUtils
import ru.netology.nework.utils.StringArg
import ru.netology.nework.viewmodel.PostsViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NewPostFragment : Fragment() {


    companion object {
        var Bundle.textArg: String? by StringArg
    }

    var type: TypeAttachment? = null

    private val postsViewModel by activityViewModels<PostsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentNewPostBinding.inflate(layoutInflater, container, false)
        val photoLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                it.data?.data.let { uri ->
                    val stream = uri?.let {
                        context?.contentResolver?.openInputStream(it)
                    }
                    postsViewModel.changeMedia(uri, stream, type)
                }
            }

        val mediaLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val stream = context?.contentResolver?.openInputStream(it)
                postsViewModel.changeMedia(it, stream, type)
            }
        }

        binding.apply {

            arguments?.textArg?.let(
                editTextContentFragmentNewPost::setText
            ) ?: postsViewModel.edited.value?.content
            editTextContentFragmentNewPost.requestFocus()


            buttonTakePhotoFragmentNewPost.setOnClickListener {
                ImagePicker.Builder(this@NewPostFragment)
                    .cameraOnly()
                    .maxResultSize(2048, 2048)
                    .createIntent(photoLauncher::launch)
                type = TypeAttachment.IMAGE
            }
            buttonChoosePhotoFragmentNewPost.setOnClickListener {
                mediaLauncher.launch("image/*")
                type = TypeAttachment.IMAGE
            }
            buttonChooseAudioFragmentNewPost.setOnClickListener {
                mediaLauncher.launch("audio/*")
                type = TypeAttachment.IMAGE
            }
            buttonVideoFragmentNewPost.setOnClickListener {
                mediaLauncher.launch("video/*")
                type = TypeAttachment.IMAGE
            }
            buttonRemoveFragmentNewPost.setOnClickListener {
                postsViewModel.changeMedia(null, null, null)
            }
            buttonCoordsFragmentNewPost.setOnClickListener {
                Toast.makeText(context, "do in future", Toast.LENGTH_SHORT).show()
            }
            postsViewModel.media.observe(viewLifecycleOwner) {
                if (it?.uri != null) {
                    postMediaImageView.loadImage(it.uri.toString())
                }
            }
            postsViewModel.postCreated.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.postsFragment)
            }
            buttonDoneFragmentNewPost.setOnClickListener {
                if (editTextContentFragmentNewPost.text.isNullOrBlank()) {
                    Toast.makeText(
                        context,
                        getString(R.string.content_must_be_filled), Toast.LENGTH_SHORT
                    ).show()
                    editTextContentFragmentNewPost.error = "Fill me!"
                } else {
                    postsViewModel.changePostContent(
                        editTextContentFragmentNewPost.text.toString()
                    )
                    postsViewModel.savePost()
                    AndroidUtils.hideKeyboard(requireView())
                }
            }




            return binding.root
        }
    }
}