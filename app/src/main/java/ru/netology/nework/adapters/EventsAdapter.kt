package ru.netology.nework.adapters


import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import ru.netology.nework.databinding.EventCardV2Binding
import ru.netology.nework.dto.Event



interface OnEventInteractionListener {

    fun onLikeEvent(event: Event)
    fun onParticipateEvent(event: Event)
    fun onShareEvent(event: Event)
    fun onShowSpeakersEvent(event: Event)
    fun onShowCoordsEvent(event: Event)

    fun deleteEvent(event: Event)
    fun editEvent(event: Event)
    fun openImage(event: Event)

    fun showLikersEvent(event: Event)
    fun showParticipantsEvent(event: Event)

}

class EventsAdapter(
    private val onEventInteractionListener: OnEventInteractionListener
): ListAdapter<Event, EventsViewHolderVersionTwo>(EventDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolderVersionTwo {
        val binding = EventCardV2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventsViewHolderVersionTwo(
            binding = binding,
            listener = onEventInteractionListener,
            context = parent.context,
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: EventsViewHolderVersionTwo, position: Int) {
        getItem(position)?.let {
            holder.binding(it)
        }
    }


}



class EventDiffCallBack: DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
        oldItem == newItem

}