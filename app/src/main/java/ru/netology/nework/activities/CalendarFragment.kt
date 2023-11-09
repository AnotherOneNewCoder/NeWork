package ru.netology.nework.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope

import androidx.lifecycle.repeatOnLifecycle
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.nework.adapters.CalendarEventAdapter
import ru.netology.nework.adapters.OnCalendarEventInteractionListener
import ru.netology.nework.databinding.FragmentCalendarBinding
import ru.netology.nework.dto.CalendarNote
import ru.netology.nework.handler.CalendarDecorator
import ru.netology.nework.utils.CommonUtils
import ru.netology.nework.viewmodel.AuthViewModel
import ru.netology.nework.viewmodel.CalendarNoteViewModel
import java.util.Calendar
import java.util.Date


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CalendarFragment: Fragment() {
    private val calendarNoteViewModel by activityViewModels<CalendarNoteViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    private lateinit var calendar: MaterialCalendarView


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCalendarBinding.inflate(layoutInflater, container, false)
        val adapter = CalendarEventAdapter(object : OnCalendarEventInteractionListener {
            override fun deleteCalendarEvent(calendarNote: CalendarNote) {
                calendarNoteViewModel.removeCalendarNoteById(calendarNote.id)
            }

        })


        binding.rwCalendarEvents.adapter = adapter

        binding.apply {
            calendar = calendarView



        }

        authViewModel.data.observe(viewLifecycleOwner) { auth ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    calendarNoteViewModel.data.collectLatest { list ->
                        val newList = list.filter { calendarNote ->
                            calendarNote.currentUserId == auth.id
                        }
                        adapter.submitList(newList)

                        newList.forEach { calendarNote ->

                            val dateToLong = CommonUtils.convertDataToLong(calendarNote.datetime)

                            val calendarDay = CalendarDay.from(Date(dateToLong))

                            val noteDecorator = CalendarDecorator(Color.RED, setOf(calendarDay), calendarNote.author)
                            calendar.addDecorator(noteDecorator)

                        }
                    }
                }
            }
        }



        return binding.root
    }


}