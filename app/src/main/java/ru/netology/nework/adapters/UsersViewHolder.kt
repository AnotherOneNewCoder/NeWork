package ru.netology.nework.adapters

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.netology.nework.databinding.UserCardBinding
import ru.netology.nework.dto.User
import ru.netology.nework.handler.loadAvatar

class UsersViewHolder(
    private val binding: UserCardBinding,
    private val listener: OnUserInteractionListener
): ViewHolder(binding.root) {
    fun bind(user: User) {
        binding.apply {
            if (user.avatar != null) {
                userAvatar.loadAvatar(user.avatar)
            }
            idOrCount.text = user.id.toString()
            userName.text = user.name
            userAction.setOnClickListener {
                listener.onUserClicked(user)
            }
        }
    }
}