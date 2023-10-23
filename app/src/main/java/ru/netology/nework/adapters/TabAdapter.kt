package ru.netology.nework.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.activities.CalendarFragment
import ru.netology.nework.activities.EventsFragment
import ru.netology.nework.activities.JobsFragment
import ru.netology.nework.activities.PostsFragment
import ru.netology.nework.activities.UsersFragment

class TabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val itemCount = 4
    override fun getItemCount(): Int {
        return itemCount
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return CalendarFragment()
            1 -> return PostsFragment()
            2 -> return EventsFragment()
            3 -> return JobsFragment()
        }
        return Fragment()
    }
}