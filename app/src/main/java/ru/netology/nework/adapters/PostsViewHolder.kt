package ru.netology.nework.adapters

import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nework.R
import ru.netology.nework.databinding.PostCardBinding
import ru.netology.nework.dto.Post
import ru.netology.nework.dto.TypeAttachment
import ru.netology.nework.handler.loadAvatar
import ru.netology.nework.handler.loadImage
import android.content.Context

import android.os.Build
import androidx.annotation.RequiresApi

import androidx.media3.exoplayer.ExoPlayer

import ru.netology.nework.dto.Coordinates
import ru.netology.nework.utils.CommonUtils

class PostsViewHolder(
    private val binding: PostCardBinding,
    private val listener: OnPostInteractionListener,
    private val context: Context,
): ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)

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
            postPublishedTime.text = CommonUtils.formatToDate(post.published)
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
            val player = ExoPlayer.Builder(context).build()
            videoPlayer.player = player
            videoPlayer.setOnClickListener {
                if (!player.isPlaying) {
                    val mediaItem = post.attachment?.url?.let { uri ->
                        androidx.media3.common.MediaItem.fromUri(
                            uri
                        )
                    }
                    if (mediaItem != null) {
                        player.setMediaItem(mediaItem)
                    }
//                val mediaSource = ProgressiveMediaSource.Factory(
//                    DefaultDataSource.Factory(context)
//                ).createMediaSource(mediaItem)
                    player.prepare()
                    player.playWhenReady

//                listener.onPlayStopVideo(post)
                }
                else player.release()
            }



            btnLike.isCheckable = true
            btnLike.isChecked = post.likedByMe
            btnLike.isCheckable = false
            btnLike.text = post.likeOwnerIds.count().toString()
            btnLike.setOnClickListener {
                listener.onLikePost(post)
            }
            btnLike.setOnLongClickListener {
                listener.showLikersList(post)
                true
            }

            btnMention.isCheckable = true
            btnMention.isChecked = post.mentionedMe
            btnMention.isCheckable = false
            btnMention.text = post.mentionIds.count().toString()
            btnMention.setOnClickListener {
                listener.onMentionPost(post)
            }
            btnMention.setOnLongClickListener {
                listener.showMentionsList(post)
                true
            }

            btnShare.setOnClickListener {
                listener.onSharePost(post)
            }
            if (post.coords != null && post.coords != Coordinates(
                    0.00000000000000000000, 0.00000000000000000000
                )
            ) {
                btnCoords.isVisible = true
                btnCoords.setOnClickListener {
                    listener.onShowCoordsPost(post)
                }
            } else {
                btnCoords.visibility = View.INVISIBLE
            }
            buttonMenuCardPost.isVisible = post.ownedByMe
            buttonMenuCardPost.setOnClickListener {
                PopupMenu(context,it).apply {
                    inflate(R.menu.edit_options)
                    setOnMenuItemClickListener { item->
                        when(item.itemId) {
                            R.id.edit -> {
                                listener.editPost(post)
                                true
                            }
                            R.id.remove -> {
                                listener.deletePost(post)
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