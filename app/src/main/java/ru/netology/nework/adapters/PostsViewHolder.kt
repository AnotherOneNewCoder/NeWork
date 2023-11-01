package ru.netology.nework.adapters

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nework.databinding.PostCardBinding
import ru.netology.nework.dto.Post
import ru.netology.nework.dto.TypeAttachment
import ru.netology.nework.handler.loadAvatar
import ru.netology.nework.handler.loadImage

class PostsViewHolder(
    private val binding: PostCardBinding,
    private val listener: OnPostInteractionListener
): ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            if (post.authorAvatar != null) {
                postAuthorAvatar.loadAvatar(post.authorAvatar)
            }
            postAuthorName.text = post.author
            if (post.authorJob != null) {
                postAuthorJob.isVisible = true
                postAuthorJob.text = post.authorJob
            } else {
                postAuthorJob.isVisible = false
            }
            postPublishedTime.text = post.published
            postContent.text = post.content
            var contentCliked = false
            postContent.setOnClickListener {
                if (!contentCliked) {
                    postContent.maxLines = Int.MAX_VALUE
                    contentCliked = true
                } else {
                    postContent.maxLines = 6
                    contentCliked = false
                }
            }

            when(post.link) {
                null -> link.visibility = View.GONE
                else -> link.visibility = View.VISIBLE
            }
            if (post.attachment != null && post.attachment.type== TypeAttachment.IMAGE) {
                postImageMedia.loadImage(post.attachment.url)
            } else {
                postImageMedia.isVisible = false
            }
            musicElements.isVisible = post.attachment != null && post.attachment.type == TypeAttachment.AUDIO
            videoElements.isVisible = post.attachment != null && post.attachment.type == TypeAttachment.VIDEO


            // отображение/скрытие полной ин-ции





            postImageMedia.setOnClickListener {
                listener.onOpenImageFullScreen(post)
            }


            var startStopMusic = true
            playStopMusicBtn.isCheckable = startStopMusic
            playStopMusicBtn.setOnClickListener {
                listener.onPlayStopMusic(post)
                playStopMusicBtn.isChecked = startStopMusic
                startStopMusic = !startStopMusic
            }

            var startStopVideo = true
            playStopVideoBtn.isCheckable = startStopVideo
            playStopVideoBtn.setOnClickListener {
                listener.onPlayStopVideo(post)
                playStopVideoBtn.isChecked = startStopVideo
                startStopVideo = !startStopVideo
            }
            videoPlayer.setOnClickListener {
                listener.onPlayStopVideo(post)
            }



            btnLike.isCheckable = true
            btnLike.isChecked = post.likedByMe
            btnLike.isCheckable = false
            btnLike.text = post.likeOwnerIds.count().toString()
            btnLike.setOnClickListener {
                listener.onLikePost(post)
            }

            btnMention.isCheckable = true
            btnMention.isChecked = post.mentionedMe
            btnMention.text = post.mentionIds.count().toString()
            btnMention.setOnClickListener {
                listener.onMentionPost(post)
            }

            btnShare.setOnClickListener {
                listener.onSharePost(post)
            }
            if (post.coords != null) {
                btnCoords.isVisible = true
                btnCoords.setOnClickListener {
                    listener.onShowCoordsPost(post)
                }
            } else {
                btnCoords.visibility = View.INVISIBLE
            }
        }
    }
}