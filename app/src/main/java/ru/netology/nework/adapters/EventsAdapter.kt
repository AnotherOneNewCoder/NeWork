package ru.netology.nework.adapters

import androidx.recyclerview.widget.ListAdapter
import ru.netology.nework.dto.Event


interface OnEventInteractionListener {
    fun onHideShowFullInfo(event: Event)
    fun onLikeEvent(event: Event)
    fun onParticipateEvent(event: Event)
    fun onShareEvent(event: Event)
    fun onShowSpeakersEvent(event: Event)
    fun onShowCoordsEvent(event: Event)

}

class EventsAdapter(
    private val onEventInteractionListener: OnEventInteractionListener
)//: ListAdapter<Event, > {
//}