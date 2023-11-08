package ru.netology.nework.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.R
import ru.netology.nework.databinding.FragmentNewPostBinding
import ru.netology.nework.dto.Coordinates
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
    private var latitude: Double? = null
    private var longitude: Double? = null

    private val postsViewModel by activityViewModels<PostsViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
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
            latitude = arguments?.getDouble("map_lat")
            longitude = arguments?.getDouble("map_long")
            arguments?.textArg?.let(
                editTextContentFragmentNewPost::setText
            ) ?: postsViewModel.edited.value?.content
            editTextContentFragmentNewPost.requestFocus()

            editTextContentFragmentNewPost.setText(
                arguments?.getString("edit_post_content") ?: postsViewModel.edited.value?.content)

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
                type = TypeAttachment.AUDIO
            }
            buttonVideoFragmentNewPost.setOnClickListener {
                mediaLauncher.launch("video/*")
                type = TypeAttachment.VIDEO
            }
            buttonRemoveFragmentNewPost.setOnClickListener {
                postsViewModel.changeMedia(null, null, null)
            }
            buttonCoordsFragmentNewPost.setOnClickListener {
                postsViewModel.changePostContent(
                    editTextContentFragmentNewPost.text.toString(),
                    null
                )
                val bundle = Bundle().apply {
                    putString("action", "new")
                    putString("fragment", "post")
                    if (latitude != null) {
                        Log.d("MyTag", "$latitude")
                        putDouble("mapLat",
                            latitude!!
                        )
                    }
                    if (longitude != null) {
                        Log.d("MyTag", "$longitude")
                        putDouble("mapLong",
                            longitude!!
                        )
                    }

                }
                findNavController().navigate(R.id.mapFragment, bundle)
            }
            postsViewModel.media.observe(viewLifecycleOwner) {
                if (it?.uri != null) {
                    postMediaImageView.loadImage(it.uri.toString())
                }
            }
            postsViewModel.postCreated.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.postsFragment)
            }
            buttonMentionFragmentNewPost.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("action", "mention")
                }
                findNavController().navigate(R.id.usersFragment, bundle)
            }

            postsViewModel.edited.observe(viewLifecycleOwner) {
                buttonMentionFragmentNewPost.apply {
                    text = "$text ${postsViewModel.edited.value?.mentionIds?.count().toString()}"
                }
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
                        editTextContentFragmentNewPost.text.toString(),
                        coordinates = if (latitude == null || longitude ==null) {
                            Coordinates(0.00, 0.00)
                        } else {
                            Coordinates(latitude, longitude)
                        },
                    )
                    postsViewModel.savePost()
                    AndroidUtils.hideKeyboard(requireView())
                }
            }




            return binding.root
        }
    }
}