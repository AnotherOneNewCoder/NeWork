package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.R
import ru.netology.nework.auth.AppAuth
import ru.netology.nework.databinding.FragmentSingInBinding
import ru.netology.nework.utils.AndroidUtils.hideKeyboard
import ru.netology.nework.viewmodel.SingInViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SingInFragment : Fragment() {

    @Inject
    lateinit var appAuth: AppAuth
    private val singInModel by viewModels<SingInViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSingInBinding.inflate(layoutInflater)


        binding.apply {
            editTextLoginFragmentSingIn.requestFocus()




            buttonSingInFragmentSingIn.setOnClickListener {
                if (!editTextLoginFragmentSingIn.text.isNullOrBlank() && !editTextPasswordFragmentSingIn.text.isNullOrBlank()) {
                    singInModel.singIn(
                        editTextLoginFragmentSingIn.text.toString(),
                        editTextPasswordFragmentSingIn.text.toString()
                    )
                } else {
                    containerTextLoginFragmentSingIn.setErrorIconOnClickListener {
                        containerTextLoginFragmentSingIn.error = null
                    }
                    containerTextPasswdFragmentSingIn.setErrorIconOnClickListener {
                        containerTextPasswdFragmentSingIn.error = null
                    }
                }
                hideKeyboard(requireView())
            }

            singInModel.data.observe(viewLifecycleOwner) {
                appAuth.setAuth(id = it.id, token = it.token)
                findNavController().navigate(R.id.nav_main)

            }

            buttonSingUnFragmentSingIn.setOnClickListener {
                findNavController().navigate(R.id.singUpFragment)
            }

            singInModel.state.observe(viewLifecycleOwner) { state ->
                when {
                    state.loginError -> {
                        containerTextLoginFragmentSingIn.error = "Wrong login or password!"
                    }
                    state.error -> {
                        Toast.makeText(context, "Check network connection", Toast.LENGTH_LONG).show()
                    }
                }
                progressBarFragmentSignIn.isVisible = state.loading
            }


        }
        return binding.root
    }
}