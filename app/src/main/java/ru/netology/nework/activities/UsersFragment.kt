package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import ru.netology.nework.R

import ru.netology.nework.adapters.OnUserInteractionListener
import ru.netology.nework.adapters.UsersAdapter
import ru.netology.nework.databinding.FragmentUsersBinding
import ru.netology.nework.dto.User
import ru.netology.nework.viewmodel.UsersViewModel
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UsersFragment: Fragment() {

    private val userModel by viewModels<UsersViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUsersBinding.inflate(layoutInflater, container, false)
        val adapter = UsersAdapter( object: OnUserInteractionListener{
            override fun onUserClicked(user: User) {
                val bundle = Bundle().apply {
                    if (user != null) {


                        putLong("profileId", user.id)
                        putString("profileName", user.name)
                        putString("profileAvatar", user.avatar)
                        putLong("userId", user.id)
                    }

                }
                findNavController().popBackStack()
                findNavController().navigate(R.id.profileFragment, bundle)
//                Toast.makeText(context, user.name, Toast.LENGTH_SHORT).show()
            }

        } )

        binding.rwUsers.adapter = adapter


        userModel.data.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        userModel.state.observe(viewLifecycleOwner) {
            when {
                it.error -> {
                    Toast.makeText(context,
                        getString(R.string.check_network_connection), Toast.LENGTH_SHORT).show()
                }
            }
            binding.progressBarFragmentUsers.isVisible = it.loading
        }


//        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (userModel.data. {  {
//                    it.name == query
//                    } })
//                return false
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                return false
//            }
//
//        })





        return binding.root
    }
}


