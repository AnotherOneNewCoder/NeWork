package ru.netology.nework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nework.databinding.CalendarEventCardBinding
import ru.netology.nework.dto.Event

class CalendarEventAdapter: ListAdapter<Event, CalendarEventViewHolder>(EventDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarEventViewHolder {
        val binding = CalendarEventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarEventViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: CalendarEventViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}