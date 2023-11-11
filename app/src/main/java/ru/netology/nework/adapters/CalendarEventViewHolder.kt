package ru.netology.nework.adapters

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nework.databinding.CalendarEventCardBinding
import ru.netology.nework.dto.CalendarNote

import ru.netology.nework.utils.CommonUtils

class CalendarEventViewHolder(
    val binding: CalendarEventCardBinding,
    val listener: OnCalendarEventInteractionListener,
): RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(calendarNote: CalendarNote) {
        binding.apply {
            calendarEventAuthorName.text = calendarNote.author
            calendarEventType.text = calendarNote.type.toString()
            calendarEventDateTime.text = CommonUtils.formatToDate(calendarNote.datetime)

            if (calendarNote.link != null && calendarNote.link != "Not presented") {
                calendarEventLink.visibility = View.VISIBLE
                shapkaCalendarEventLink.visibility = View.VISIBLE
                calendarEventLink.text = calendarNote.link
            } else {
                calendarEventLink.visibility = View.GONE
                shapkaCalendarEventLink.visibility = View.GONE
            }
            buttonDelete.setOnClickListener {
                listener.deleteCalendarEvent(calendarNote)
            }

        }
    }
}