package ru.netology.nework.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.netology.nework.R
import ru.netology.nework.adapters.OnPostInteractionListener
import ru.netology.nework.adapters.PostsAdapter
import ru.netology.nework.databinding.FragmentPostsBinding
import ru.netology.nework.dto.Post
import ru.netology.nework.dto.TypeAttachment
import ru.netology.nework.media.MediaLifecycleObserver
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
    private val mediaObserver = MediaLifecycleObserver()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostsBinding.inflate(layoutInflater, container, false)
        lifecycle.addObserver(mediaObserver)
        val adapter = PostsAdapter(object : OnPostInteractionListener {


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
                if (authViewModel.isAuthorized) {
                    if (post.ownedByMe) {
                        postViewModel.edit(post)
                        val bundle = Bundle().apply {
                            putString("action", "mention")
                        }
                        findNavController().navigate(R.id.usersFragment, bundle)
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.only_post_owner_can_mention_users),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    binding.rwPosts.findNavController().navigate(R.id.singInDialog)
                }
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
                mediaObserver.apply {
                    if (player?.isPlaying == true) {
                        player?.pause()
                        player?.reset()
                        return
                    } else
                        if (post.attachment?.type == TypeAttachment.AUDIO) {
                            player?.setDataSource(post.attachment.url)
                        }
                }.play()
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
                        putDouble("map_lat", it)
                        Log.d("MyTag", "Lat: $it")
                    }
                    post.coords?.long?.let {
                        putDouble("map_long", it)
                        Log.d("MyTag", "Long: $it")
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
                        findNavController().navigate(R.id.action_profileFragment_to_bottomSheetFragment)
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
                        findNavController().navigate(R.id.action_profileFragment_to_bottomSheetFragment)
                    }
                } else {
                    binding.rwPosts.findNavController().navigate(R.id.singInDialog)
                }
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
                binding.emptyTextFragmentUsersPosts.isVisible = it.isEmpty()
            }
        } else {
            Toast.makeText(context, getString(R.string.id_null), Toast.LENGTH_SHORT).show()
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
            wallViewModel.loadUserPosts(id)
            binding.refresher.isRefreshing = false
        }

        return binding.root
    }
}