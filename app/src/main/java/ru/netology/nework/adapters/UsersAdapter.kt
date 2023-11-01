package ru.netology.nework.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.netology.nework.dto.User
import androidx.recyclerview.widget.ListAdapter
import ru.netology.nework.databinding.UserCardBinding


interface OnUserInteractionListener {
    fun onUserClicked(user: User)


}

class UsersAdapter(
    private val  onUserInteractionListener: OnUserInteractionListener
): ListAdapter<User, UsersViewHolder>(UsersDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = UserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(
            binding,
            onUserInteractionListener
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }



}

class UsersDiffCallBack: DiffUtil.ItemCallback<User>(){
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

}