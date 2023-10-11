package ru.netology.nework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.netology.nework.dto.Post
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nework.databinding.PostCardBinding


interface OnPostInteractionListener {
    fun onHideShowFullInfo(post: Post)
    fun onLikePost(post: Post)
    fun onMentionPost(post: Post)
    fun onSharePost(post: Post)
    fun onShowCoordsPost(post: Post)
    fun onOpenImageFullScreen(post: Post)
    fun onPlayStopMusic(post: Post)
    fun onPlayStopVideo(post: Post)


}
class PostsAdapter(
    private val listener: OnPostInteractionListener,
): ListAdapter<Post, PostsViewHolder>(PostDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(
            binding,
            listener
        )
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}



class PostDiffCallBack: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem

}