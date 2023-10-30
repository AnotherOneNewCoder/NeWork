package ru.netology.nework.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.R
import ru.netology.nework.adapters.TabAdapter
import ru.netology.nework.auth.AppAuth
import ru.netology.nework.databinding.FragmentProfileBinding
import ru.netology.nework.dto.Event
import ru.netology.nework.handler.loadAvatar
import ru.netology.nework.viewmodel.AuthViewModel
import ru.netology.nework.viewmodel.EventsViewModel
import ru.netology.nework.viewmodel.UsersViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment : Fragment() {
    @Inject
    lateinit var appAuth: AppAuth

    private val authViewModel by viewModels<AuthViewModel>()
    private val usersViewModel by viewModels<UsersViewModel>()
    private val eventsViewModel by viewModels<EventsViewModel>()

    private val tabTitles = arrayOf(
        R.string.tab_ic_calendar,
        R.string.user_s_events,
        R.string.user_s_posts,
        R.string.user_s_jobs,
    )
//    private val tabIcons = arrayOf(
//        R.drawable.ic_calendar,
//        R.drawable.ic_events,
//        R.drawable.ic_posts,
//        R.drawable.ic_job
//    )
    private var addGroupVisible = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(layoutInflater)

        val id = arguments?.getLong("profileId")
        val name = arguments?.getString("profileName")
        val avatar = arguments?.getString("profileAvatar")



        val viewPager = binding.viewPagerProfile
        val tabLayout = binding.profileTabLayout
        viewPager.adapter = TabAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(tabTitles[position])
        }.attach()

//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            //tab.icon = Drawable.createFromPath(tabIcons[position])
//            tab.setIcon(tabIcons[position])
//        }.attach()



        authViewModel.data.observe(viewLifecycleOwner) {
            if (authViewModel.isAuthorized && it.id != 0L && binding.idOrCount.text == it.id.toString()) {
                binding.add.visibility = View.VISIBLE


            } else {
                binding.add.visibility = View.INVISIBLE
            }
        }


        binding.apply {
            if (avatar != null) {
                wallUserAvatar.loadAvatar(avatar)
            }
            wallUserName.text = name
            idOrCount.text = id.toString()

            btnLogOut.setOnClickListener {
                appAuth.removeAuth()
                findNavController().navigate(R.id.nav_main)

            }
            idOrCount.text = id.toString()
            tokenTest.text = appAuth.authSateFlow.value.token

            add.setOnClickListener {
                addGroupVisible = !addGroupVisible
                when (addGroupVisible) {
                    true -> {
                        groupAdd.visibility = View.VISIBLE
                        add.setImageResource(R.drawable.minus)
                        addJob.setOnClickListener {
                            findNavController().navigate(R.id.newJobFragment)
                            addGroupVisible = !addGroupVisible
                        }
                        addEvent.setOnClickListener {
                            eventsViewModel.edit(Event.emptyEvent)
                            findNavController().navigate(R.id.newEventFragment)
                            addGroupVisible = !addGroupVisible
                        }
                        addPost.setOnClickListener {
                            findNavController().navigate(R.id.newPostFragment)
                            addGroupVisible = !addGroupVisible
                        }
                    }

                    false -> {
                        groupAdd.visibility = View.GONE
                        add.setImageResource(R.drawable.plus)
                    }
                }
            }
        }


        return binding.root
    }
}