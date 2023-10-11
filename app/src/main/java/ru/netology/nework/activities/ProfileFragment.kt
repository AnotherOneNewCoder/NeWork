package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.netology.nework.R
import ru.netology.nework.adapters.TabAdapter
import ru.netology.nework.databinding.FragmentProfileBinding

class ProfileFragment: Fragment() {

    private val tabTitles = arrayOf(
        R.string.tab_ic_calendar,
        R.string.user_s_events,
        R.string.user_s_posts,
        R.string.user_s_jobs,
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(layoutInflater)

        val viewPager = binding.viewPagerProfile
        val tabLayout = binding.profileTabLayout
        viewPager.adapter = TabAdapter(this)
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = getString(tabTitles[position])
        }.attach()


        return binding.root
    }
}