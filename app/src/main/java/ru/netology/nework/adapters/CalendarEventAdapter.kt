package ru.netology.nework.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nework.databinding.CalendarEventCardBinding
import ru.netology.nework.dto.CalendarNote


interface OnCalendarEventInteractionListener {
    fun deleteCalendarEvent(calendarNote: CalendarNote)
}

class CalendarEventAdapter(
    private val listener: OnCalendarEventInteractionListener,
): ListAdapter<CalendarNote, CalendarEventViewHolder>(CalendarNoteDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarEventViewHolder {
        val binding = CalendarEventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarEventViewHolder(
            binding,
            listener,
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarEventViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }


}

class CalendarNoteDiffCallBack: DiffUtil.ItemCallback<CalendarNote>() {
    override fun areItemsTheSame(oldItem: CalendarNote, newItem: CalendarNote): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CalendarNote, newItem: CalendarNote): Boolean =
        oldItem == newItem

}