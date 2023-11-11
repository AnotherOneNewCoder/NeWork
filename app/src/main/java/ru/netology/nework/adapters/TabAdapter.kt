package ru.netology.nework.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.activities.CalendarFragment
import ru.netology.nework.activities.JobsFragment
import ru.netology.nework.activities.UserEventFragment
import ru.netology.nework.activities.WallFragment

class TabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val itemCount = 4
    override fun getItemCount(): Int {
        return itemCount
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun createFragment(position: Int): Fragment {
        when(position) {
            3 -> return CalendarFragment()
            0 -> return UserEventFragment()
            1 -> return WallFragment()
            2 -> return JobsFragment()
        }
        return Fragment()
    }
}