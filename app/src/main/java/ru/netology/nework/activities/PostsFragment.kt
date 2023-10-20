package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import ru.netology.nework.adapters.OnPostInteractionListener
import ru.netology.nework.adapters.PostsAdapter
import ru.netology.nework.databinding.FragmentPostsBinding
import ru.netology.nework.dto.Post
import ru.netology.nework.viewmodel.PostsViewModel




@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostsFragment: Fragment() {
    private val postViewModel by activityViewModels<PostsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostsBinding.inflate(layoutInflater, container, false)

        val adapter = PostsAdapter(object : OnPostInteractionListener{
            override fun onHideShowFullInfo(post: Post) {

            }

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

        lifecycleScope.launchWhenCreated {
            postViewModel.data.collectLatest {
                adapter.submitList(it)
            }
        }


        return binding.root
    }
}