package ru.netology.nework.adapters

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nework.R
import ru.netology.nework.databinding.EventCardBinding
import ru.netology.nework.databinding.EventCardV2Binding
import ru.netology.nework.dto.Event
import ru.netology.nework.dto.TypeAttachment
import ru.netology.nework.handler.loadAvatar
import ru.netology.nework.handler.loadImage

class EventsViewHolderVersionTwo(
    private val binding: EventCardV2Binding,
    private val listener: OnEventInteractionListener,
    private val context: Context,
) : ViewHolder(binding.root) {
    fun binding(event: Event) {
        binding.apply {
            if (event.authorAvatar != null) {
                eventAuthorAvatar.loadAvatar(event.authorAvatar)
            }
            eventAuthorName.text = event.author
            if (event.authorJob != null) {
                eventAuthorJob.isVisible = true
                eventAuthorJob.text = event.authorJob
            } else {
                eventAuthorJob.isVisible = false
            }
            idOrCount.text = event.authorId.toString()
            eventPublishedTime.text = event.published
            eventContent.text = event.content
            var contentCliked = false
            eventContent.setOnClickListener {
                if (!contentCliked) {
                    eventContent.maxLines = Int.MAX_VALUE
                    contentCliked = true
                } else {
                    eventContent.maxLines = 6
                    contentCliked = false
                }
            }
            eventDateTime.text = event.datetime
            eventFormat.text = event.type.toString()
            when (event.link) {
                null -> link.visibility = View.GONE
                else -> link.visibility = View.VISIBLE
            }




            if (event.attachment != null && event.attachment.type == TypeAttachment.IMAGE) {
                eventMedia.visibility = View.VISIBLE
                eventMedia.loadImage(event.attachment.url)
            } else {
                eventMedia.visibility = View.GONE
            }
            btnLike.isCheckable = true
            btnLike.isChecked = event.likedByMe
            btnLike.isCheckable = false

            btnLike.text = event.likeOwnerIds.count().toString()
            btnLike.setOnClickListener {
                listener.onLikeEvent(event)
            }
            btnParticipate.text = event.participantsIds.count().toString()

            btnParticipate.isCheckable = true
            btnParticipate.isChecked = event.participatedByMe
            btnParticipate.isCheckable = false

            btnParticipate.setOnClickListener {
                listener.onParticipateEvent(event)
            }
            btnSpeakers.text = event.speakerIds.count().toString()
            btnSpeakers.setOnClickListener {
                listener.onShowSpeakersEvent(event)
            }
            btnShare.setOnClickListener {
                listener.onShareEvent(event)
            }
            if (event.coords != null) {
                btnCoords.isVisible = true
                btnCoords.setOnClickListener {
                    listener.onShowCoordsEvent(event)
                }
            } else {
                btnCoords.visibility = View.INVISIBLE
            }
            buttonMenuCardEvent.isVisible = event.ownedByMe
            buttonMenuCardEvent.setOnClickListener {
                PopupMenu(context,it).apply {
                    inflate(R.menu.edit_options)
                    setOnMenuItemClickListener { item->
                        when(item.itemId) {
                            R.id.edit -> {
                                listener.editEvent(event)
                                true
                            }
                            R.id.remove -> {
                                listener.deleteEvent(event)
                                true
                            }
                            else -> false

                        }
                    }
                }.show()
            }

        }
    }
}