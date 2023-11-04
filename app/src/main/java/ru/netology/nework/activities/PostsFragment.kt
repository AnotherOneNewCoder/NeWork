package ru.netology.nework.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import ru.netology.nework.R
import ru.netology.nework.adapters.OnPostInteractionListener
import ru.netology.nework.adapters.PostsAdapter
import ru.netology.nework.databinding.FragmentPostsBinding
import ru.netology.nework.dto.Post
import ru.netology.nework.viewmodel.AuthViewModel
import ru.netology.nework.viewmodel.PostsViewModel
import ru.netology.nework.viewmodel.UsersViewModel


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostsFragment: Fragment() {
    private val postViewModel by activityViewModels<PostsViewModel>()
    private val usersViewModel by activityViewModels<UsersViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostsBinding.inflate(layoutInflater, container, false)

        val adapter = PostsAdapter(object : OnPostInteractionListener{


            override fun onLikePost(post: Post) {
                if (authViewModel.isAuthorized) {
                    if (!post.likedByMe) {
                        postViewModel.likeById(post.id)
                    } else {
                        postViewModel.unlikeById(post.id)
                    }
                } else {
                    binding.rwPosts.findNavController().navigate(R.id.singInDialog)
                }
            }

            override fun onMentionPost(post: Post) {

            }

            override fun onSharePost(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val sharePost = Intent.createChooser(intent, "Share Post")
                startActivity(sharePost)
            }

            override fun onShowCoordsPost(post: Post) {
                val coords = post.coords
                val lat = coords?.lat
                val long = coords?.long
                val bundle = Bundle().apply {
                    if (lat != null && long != null) {
                        putDouble("mapLat", lat)
                        putDouble("mapLong", long)


                    }
                }
                findNavController().navigate(R.id.mapFragment, bundle)
            }

            override fun onOpenImageFullScreen(post: Post) {
                val bundle = Bundle().apply {
                    putString("attach_img", post.attachment?.url)
                }
                findNavController().navigate(R.id.imageAttachFragment, bundle)
            }

            override fun onPlayStopMusic(post: Post) {
                try {
                    val uri = Uri.parse(post.attachment?.url)
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri, "audio/*")
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, getString(R.string.nothing_to_play), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onPlayStopVideo(post: Post) {
                try {
                    val uri = Uri.parse(post.attachment?.url)
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri, "video/*")
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, getString(R.string.nothing_to_play), Toast.LENGTH_SHORT).show()
                }
            }

            override fun deletePost(post: Post) {
                postViewModel.removeById(post.id)
            }

            override fun editPost(post: Post) {
                postViewModel.edit(post)
                val bundle = Bundle().apply {
                    putString("edit_post_content", post.content)
                    putString("edit_post_published", post.published)
                    post.coords?.lat?.let {
                        putDouble("edit_post_lat", it)
                    }
                    post.coords?.long?.let {
                        putDouble("edit_post_long", it)
                    }
                }
                findNavController().navigate(R.id.newPostFragment, bundle)
            }

            override fun showLikersList(post: Post) {
                if (authViewModel.isAuthorized) {
                    if (post.likeOwnerIds.isEmpty()) {
                        Toast.makeText(
                            context,
                            getString(R.string.no_one_liked_it), Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        usersViewModel.getUsersIds(post.likeOwnerIds)
                        findNavController().navigate(R.id.action_postsFragment_to_bottomSheetFragment)
                    }
                } else {
                    binding.rwPosts.findNavController().navigate(R.id.singInDialog)
                }
            }

            override fun showMentionsList(post: Post) {
                if (authViewModel.isAuthorized) {
                    if (post.mentionIds.isEmpty()) {
                        Toast.makeText(
                            context,
                            getString(R.string.nobody_mentioned), Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        usersViewModel.getUsersIds(post.mentionIds)
                        findNavController().navigate(R.id.action_postsFragment_to_bottomSheetFragment)
                    }
                } else {
                    binding.rwPosts.findNavController().navigate(R.id.singInDialog)
                }
            }

        })
        binding.rwPosts.adapter = adapter

        postViewModel.data.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        postViewModel.state.observe(viewLifecycleOwner) {
            when {
                it.error -> {
                    Toast.makeText(context, "Check internet connection!", Toast.LENGTH_SHORT).show()
                }
            }
            binding.progressBarFragmentPosts.isVisible = it.loading
        }

        binding.refresher.setColorSchemeResources(R.color.news)
        binding.refresher.setOnRefreshListener {
            postViewModel.refreshEvents()
            binding.refresher.isRefreshing = false
        }


        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        postViewModel.refreshEvents()
        super.onCreate(savedInstanceState)
    }
}