package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

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
import ru.netology.nework.viewmodel.EventsViewModel
import ru.netology.nework.viewmodel.PostsViewModel
import ru.netology.nework.viewmodel.UsersViewModel
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UsersFragment: Fragment() {

    private val userModel by viewModels<UsersViewModel>()
    private val postsViewModel by activityViewModels<PostsViewModel>()
    private val eventsViewModel by activityViewModels<EventsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUsersBinding.inflate(layoutInflater, container, false)

        val action = arguments?.getString("action")

        val adapter = UsersAdapter( object: OnUserInteractionListener{
            override fun onUserClicked(user: User) {
                when(action) {
                    "speaker" -> {
                        eventsViewModel.setSpeaker(user.id)
                        findNavController().navigateUp()
                    }

                    "mention" -> {
                        postsViewModel.setMentionIds(user.id)
                        findNavController().navigateUp()
                    }

                    else -> {
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
                    }
                }

            }

        } )

        binding.rwUsers.adapter = adapter


        userModel.data.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }


        fun searchUser(query: String) {
            val searchQuery = "%$query%"
            userModel.searchUser(searchQuery).observe(viewLifecycleOwner) {list ->
                list.let {
                    adapter.submitList(it)
                }
            }
        }


        binding.searchBar.setOnQueryTextListener(object: OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    searchUser(query)
                }
                return true
            }

        })
        userModel.state.observe(viewLifecycleOwner) {
            when {
                it.error -> {
                    Toast.makeText(context,
                        getString(R.string.check_network_connection), Toast.LENGTH_SHORT).show()
                }
            }
            binding.progressBarFragmentUsers.isVisible = it.loading
        }










        return binding.root
    }
}


