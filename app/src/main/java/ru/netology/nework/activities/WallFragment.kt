package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.netology.nework.R
import ru.netology.nework.adapters.OnPostInteractionListener
import ru.netology.nework.adapters.PostsAdapter
import ru.netology.nework.databinding.FragmentPostsBinding
import ru.netology.nework.dto.Post
import ru.netology.nework.viewmodel.AuthViewModel
import ru.netology.nework.viewmodel.PostsViewModel
import ru.netology.nework.viewmodel.UsersViewModel
import ru.netology.nework.viewmodel.WallViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WallFragment: Fragment() {
    private val postViewModel by activityViewModels<PostsViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val usersViewModel by activityViewModels<UsersViewModel>()
    private val wallViewModel by activityViewModels<WallViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostsBinding.inflate(layoutInflater, container, false)

        val adapter = PostsAdapter(object : OnPostInteractionListener {


            override fun onLikePost(post: Post) {

            }

            override fun onMentionPost(post: Post) {

            }

            override fun onSharePost(post: Post) {

            }

            override fun onShowCoordsPost(post: Post) {

            }

            override fun onOpenImageFullScreen(post: Post) {

            }

            override fun onPlayStopMusic(post: Post) {

            }

            override fun onPlayStopVideo(post: Post) {

            }

        })
        binding.rwPosts.adapter = adapter
        var id: Long = 0L
        usersViewModel.userId.observe(activity as LifecycleOwner) {
            id = it
        }

        if (id != 0L) {
            wallViewModel.loadUserPosts(id)
            postViewModel.data.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        } else {
            Toast.makeText(context, getString(R.string.id_null), Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}