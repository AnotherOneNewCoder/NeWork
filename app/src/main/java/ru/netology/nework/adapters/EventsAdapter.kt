package ru.netology.nework.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nework.databinding.EventCardBinding
import ru.netology.nework.dto.Event



interface OnEventInteractionListener {

    fun onLikeEvent(event: Event)
    fun onParticipateEvent(event: Event)
    fun onShareEvent(event: Event)
    fun onShowSpeakersEvent(event: Event)
    fun onShowCoordsEvent(event: Event)

}

class EventsAdapter(
    private val onEventInteractionListener: OnEventInteractionListener
): ListAdapter<Event, EventsViewHolder>(EventDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventsViewHolder(
            binding = binding,
            listener = onEventInteractionListener
        )
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
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