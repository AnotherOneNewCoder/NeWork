package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import ru.netology.nework.R
import ru.netology.nework.adapters.TabAdapter
import ru.netology.nework.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val tabTitles = arrayOf(
        R.string.tab_ic_calendar,
        R.string.user_s_events,
        R.string.user_s_posts,
        R.string.user_s_jobs,
    )
    private var addGroupVisible = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(layoutInflater)

        val viewPager = binding.viewPagerProfile
        val tabLayout = binding.profileTabLayout
        viewPager.adapter = TabAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(tabTitles[position])
        }.attach()
        binding.apply {
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