package ru.netology.nework.activities


import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.R
import ru.netology.nework.auth.AppAuth
import ru.netology.nework.databinding.FragmentSingUpBinding
import ru.netology.nework.viewmodel.SingUpViewModel
import ru.netology.nework.utils.AndroidUtils
import ru.netology.nework.viewmodel.AuthViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SingUpFragment : Fragment() {

    @Inject
    lateinit var appAuth: AppAuth

    private val singUpViewModel by viewModels<SingUpViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSingUpBinding.inflate(layoutInflater)

        singUpViewModel.data.observe(viewLifecycleOwner) {
            if (it.id != 0L) {
                appAuth.setAuth(it.id, it.token)
                findNavController().navigate(R.id.nav_main)
            }
        }

        val pickPhotoLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                when (it.resultCode) {
                    ImagePicker.RESULT_ERROR -> {
                        Snackbar.make(
                            binding.root,
                            ImagePicker.getError(it.data),
                            Snackbar.LENGTH_LONG
                        ).show()

                    }

                    Activity.RESULT_OK -> {
                        val uri: Uri?= it.data?.data
                        singUpViewModel.setPhoto(uri)
                    }
                }
            }




        binding.apply {
            buttonSingUpFragmentSingUp.setOnClickListener {
                let {
                    if (it.editTextLoginFragmentSingUn.text.isNullOrBlank() ||
                        it.editTextPasswordFragmentSingUp.text.isNullOrBlank() ||
                        it.editTextUserNameFragmentSingUp.text.isNullOrBlank() ||
                        it.editTextConfirmPasswordFragmentSingUp.text.isNullOrBlank()
                    ) {
                        Toast.makeText(
                            context,
                            getString(R.string.all_fields_must_be_filled), Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (it.editTextPasswordFragmentSingUp.text.toString() == it.editTextConfirmPasswordFragmentSingUp.text.toString()) {
                            singUpViewModel.singUp(
                                login = editTextLoginFragmentSingUn.text.toString(),
                                passwd = editTextPasswordFragmentSingUp.text.toString(),
                                name = editTextUserNameFragmentSingUp.text.toString(),
                            )
                            AndroidUtils.hideKeyboard(requireView())
                        } else {
                            containerTextConfirmPasswordFragmentSingUp.error =
                                getString(R.string.mismatch_password)
                        }
                    }
                }
            }
            loadFromGallery.setOnClickListener {
                ImagePicker.with(requireActivity())
                    .crop()
                    .compress(2048)
                    .galleryOnly()
                    .galleryMimeTypes(
                        arrayOf(
                            "image/png",
                            "image/jpeg",
                            "image/jpg",

                        )
                    )
                    .createIntent(pickPhotoLauncher::launch)

            }
            takePhoto.setOnClickListener {
                ImagePicker.with(requireActivity())
                    .crop()
                    .compress(2048)
                    .cameraOnly()

                    .createIntent(pickPhotoLauncher::launch)
            }
            clearPhoto.setOnClickListener {
                singUpViewModel.clearPhoto()
            }
        }

        singUpViewModel.state.observe(viewLifecycleOwner) {
            when {
                it.error -> {
                    Toast.makeText(context,
                        getString(R.string.check_network_connection), Toast.LENGTH_SHORT).show()
                }
            }
            binding.progressBarFragmentSignUp.isVisible = it.loading
        }

        singUpViewModel.photo.observe(viewLifecycleOwner) {
            if (it.uri == null) {
                return@observe
            }
            //binding.registrationAvatar.loadAvatar(it.uri.toString())
            binding.registrationAvatar.setImageURI(it.uri)
        }




        return binding.root
    }
}
