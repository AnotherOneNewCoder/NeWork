package ru.netology.nework.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import ru.netology.nework.R
import ru.netology.nework.adapters.EventsAdapter
import ru.netology.nework.adapters.OnEventInteractionListener
import ru.netology.nework.databinding.FragmentEventsBinding
import ru.netology.nework.dto.Event
import ru.netology.nework.viewmodel.AuthViewModel
import ru.netology.nework.viewmodel.EventsViewModel
import ru.netology.nework.viewmodel.UsersViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EventsFragment : Fragment() {
    private val eventViewModel by activityViewModels<EventsViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val usersViewModel by activityViewModels<UsersViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEventsBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = EventsAdapter(
            object : OnEventInteractionListener {


                override fun onLikeEvent(event: Event) {
                    if (authViewModel.isAuthorized) {
                        if (!event.likedByMe) {
                            eventViewModel.likeById(event.id)
                        } else {
                            eventViewModel.unlikeById(event.id)
                        }
                    } else {
                        // to do dialog
                        //Toast.makeText(context, "Logging first!", Toast.LENGTH_SHORT).show()
                        binding.rwEvents.findNavController().navigate(R.id.singInDialog)
                    }
                }

                override fun onParticipateEvent(event: Event) {
                    if (authViewModel.isAuthorized) {
                        if (!event.participatedByMe) {
                            eventViewModel.participateById(event.id)
                        } else {
                            eventViewModel.dontParticipateById(event.id)
                        }
                    } else {
                        // to do dialog
                        //Toast.makeText(context, "Logging first!", Toast.LENGTH_SHORT).show()
                        binding.rwEvents.findNavController().navigate(R.id.singInDialog)
                    }
                }

                override fun onShareEvent(event: Event) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, event.content)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(intent, "share event")
                    startActivity(shareIntent)
                }

                override fun onShowSpeakersEvent(event: Event) {
                    if (authViewModel.isAuthorized) {
                        if (event.speakerIds.isEmpty()) {
                            Toast.makeText(
                                context,
                                getString(R.string.no_speakers_presented), Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            usersViewModel.getUsersIds(event.speakerIds)
                            findNavController().navigate(R.id.action_eventsFragment_to_bottomSheetFragment)
                        }
                    } else {
                        binding.rwEvents.findNavController().navigate(R.id.singInDialog)
                    }
                }

                override fun onShowCoordsEvent(event: Event) {

                    val coords = event.coords
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

                override fun deleteEvent(event: Event) {
                    eventViewModel.removeEventById(event.id)
                }

                override fun editEvent(event: Event) {
                    eventViewModel.edit(event)
                    val bundle = Bundle().apply {
                        putString("edit_event_content", event.content)
                        putString("edit_event_datetime", event.datetime)
                        event.coords?.lat?.let {
                            putDouble("map_lat", it)
                        }
                        event.coords?.long?.let {
                            putDouble("map_long", it)
                        }
                    }
                    findNavController().navigate(R.id.newEventFragment, bundle)
                }

                override fun openImage(event: Event) {
                    val bundle = Bundle().apply {
                        putString("attach_img", event.attachment?.url)
                    }
                    findNavController().navigate(R.id.imageAttachFragment, bundle)
                }

                override fun showLikersEvent(event: Event) {
                    if (authViewModel.isAuthorized) {
                        if (event.likeOwnerIds.isEmpty()) {
                            Toast.makeText(
                                context,
                                getString(R.string.no_one_liked_it), Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            usersViewModel.getUsersIds(event.likeOwnerIds)
                            findNavController().navigate(R.id.action_eventsFragment_to_bottomSheetFragment)
                        }
                    } else {
                        binding.rwEvents.findNavController().navigate(R.id.singInDialog)
                    }
                }

                override fun showParticipantsEvent(event: Event) {
                    if (authViewModel.isAuthorized) {
                        if (event.participantsIds.isEmpty()) {
                            Toast.makeText(
                                context,
                                getString(R.string.no_one_going_to_participate), Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            usersViewModel.getUsersIds(event.participantsIds)
                            findNavController().navigate(R.id.action_eventsFragment_to_bottomSheetFragment)
                        }
                    } else {
                        binding.rwEvents.findNavController().navigate(R.id.singInDialog)
                    }
                }

            }
        )
        binding.rwEvents.adapter = adapter

        eventViewModel.data.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        eventViewModel.state.observe(viewLifecycleOwner) {
            when {
                it.error -> {
                    Toast.makeText(context,
                        getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
            binding.progressBarFragmentEvents.isVisible = it.loading
        }

        binding.refresher.setColorSchemeResources(R.color.news)
        binding.refresher.setOnRefreshListener {
            eventViewModel.refreshEvents()
            binding.refresher.isRefreshing = false
        }











        return binding.root
    }
}