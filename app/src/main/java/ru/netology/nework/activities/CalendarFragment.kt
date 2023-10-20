package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import ru.netology.nework.adapters.CalendarEventAdapter
import ru.netology.nework.databinding.FragmentCalendarBinding
import ru.netology.nework.viewmodel.EventsViewModel


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CalendarFragment: Fragment() {
    private val eventViewModel by activityViewModels<EventsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCalendarBinding.inflate(layoutInflater, container, false)

        val adapter= CalendarEventAdapter()
        binding.rwCalendarEvents.adapter = adapter
        lifecycleScope.launchWhenCreated {
            eventViewModel.data.collectLatest {
                adapter.submitList(it)
            }
        }


        return binding.root
    }
}