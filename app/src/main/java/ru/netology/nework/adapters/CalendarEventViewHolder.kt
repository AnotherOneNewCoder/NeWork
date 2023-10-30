package ru.netology.nework.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nework.databinding.CalendarEventCardBinding
import ru.netology.nework.dto.Event

class CalendarEventViewHolder(
    val binding: CalendarEventCardBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(event: Event) {
        binding.apply {
            calendarEventAuthorName.text = event.author
            calendarEventType.text = event.type.toString()
            calendarEventDateTime.text = event.datetime
            if (event.coords != null) {
                calendarEventCoords.visibility = View.VISIBLE
                shapkaCalendarEventCoords.visibility = View.VISIBLE
                calendarEventCoords.text = "${event.coords.lat}, ${event.coords.long}"
            } else {
                calendarEventCoords.visibility = View.GONE
                shapkaCalendarEventCoords.visibility = View.GONE
            }
            if (event.link != null) {
                calendarEventLink.visibility = View.VISIBLE
                shapkaCalendarEventLink.visibility = View.VISIBLE
                calendarEventLink.text = event.link
            } else {
                calendarEventLink.visibility = View.GONE
                shapkaCalendarEventLink.visibility = View.GONE
            }

        }
    }
}