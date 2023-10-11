package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import ru.netology.nework.adapters.OnUserInteractionListener
import ru.netology.nework.adapters.UsersAdapter
import ru.netology.nework.databinding.FragmentUsersBinding
import ru.netology.nework.dto.User
import ru.netology.nework.viewmodel.UsersViewModel

class UsersFragment: Fragment() {

    private val userModel by activityViewModels<UsersViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUsersBinding.inflate(layoutInflater, container, false)
        val adapter = UsersAdapter( object: OnUserInteractionListener{
            override fun onUserClicked(user: User) {
                Toast.makeText(context, user.name, Toast.LENGTH_SHORT).show()
            }

        } )
//        val userTest = User(
//            id = 33L,
//            login = "33",
//            name = "Sveta",
//            avatar = null
//        )

        binding.rwUsers.adapter = adapter
        lifecycleScope.launchWhenCreated {
            userModel.data.collectLatest {
                adapter.submitList(it)
            }
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


