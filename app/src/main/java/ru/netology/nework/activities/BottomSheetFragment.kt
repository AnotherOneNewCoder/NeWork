package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.netology.nework.adapters.OnUserInteractionListener
import ru.netology.nework.adapters.UsersAdapter
import ru.netology.nework.databinding.FragmentBottomSheetBinding
import ru.netology.nework.dto.User
import ru.netology.nework.viewmodel.UsersViewModel

class BottomSheetFragment : BottomSheetDialogFragment() {
    private val usersViewModel by activityViewModels<UsersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBottomSheetBinding.inflate(inflater, container, false)

        val adapter = UsersAdapter(object : OnUserInteractionListener {
            override fun onUserClicked(user: User) {
                Toast.makeText(context, "${user.name}", Toast.LENGTH_SHORT).show()
            }

        })
        binding.rvFragmentBottomSheet.adapter = adapter

        usersViewModel.data.observe(viewLifecycleOwner) {
            if (usersViewModel.usersIds.value != null) {
                adapter.submitList(it.filter { user ->
                    usersViewModel.usersIds.value!!.contains(user.id)
                })
            }
        }


        return binding.root
    }
}