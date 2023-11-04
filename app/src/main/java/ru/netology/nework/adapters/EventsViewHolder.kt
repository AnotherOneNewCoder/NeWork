package ru.netology.nework.adapters

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nework.databinding.EventCardBinding
import ru.netology.nework.dto.Event
import ru.netology.nework.dto.TypeAttachment
import ru.netology.nework.handler.loadAvatar
import ru.netology.nework.handler.loadImage
import ru.netology.nework.utils.CommonUtils

class EventsViewHolder(
    private val binding: EventCardBinding,
    private val listener: OnEventInteractionListener
) : ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
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
            eventPublishedTime.text = CommonUtils.formatToDate(event.published)
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
            eventDateTime.text = CommonUtils.formatToDate(event.datetime)
            eventFormat.text = event.type.toString()
            when (event.link) {
                null -> link.visibility = View.GONE
                else -> link.visibility = View.VISIBLE
            }


            // отображение/скрытие полной ин-ции

            var check = false
            btnShowHide.isCheckable = true
            btnShowHide.setOnClickListener {
                if (!check) {
                    fullContent.visibility = View.VISIBLE
                    btnShowHide.isChecked = !check
                    check = !check
                } else {
                    fullContent.visibility = View.GONE
                    check = !check
                }
            }
            if (event.attachment != null && event.attachment.type == TypeAttachment.IMAGE) {
                eventMedia.loadImage(event.attachment.url)
            } else {
                eventMedia.isVisible = false
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
            eventMedia.setOnClickListener {
                listener.openImage(event)
            }



        }
    }
}