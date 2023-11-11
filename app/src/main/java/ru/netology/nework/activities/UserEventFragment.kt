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
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.R
import ru.netology.nework.adapters.EventsAdapter
import ru.netology.nework.adapters.OnEventInteractionListener
import ru.netology.nework.databinding.FragmentEventsBinding
import ru.netology.nework.dto.Event
import ru.netology.nework.viewmodel.AuthViewModel
import ru.netology.nework.viewmodel.EventsViewModel
import ru.netology.nework.viewmodel.UsersViewModel

class UserEventFragment : Fragment() {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val eventViewModel by activityViewModels<EventsViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val usersViewModel by activityViewModels<UsersViewModel>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEventsBinding.inflate(layoutInflater, container, false)

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
                            findNavController().navigate(R.id.action_profileFragment_to_bottomSheetFragment)
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
                            Log.d("MyTag", "Lat: $it")
                        }
                        event.coords?.long?.let {
                            putDouble("map_long", it)
                            Log.d("MyTag", "Long: $it")
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
                            findNavController().navigate(R.id.action_profileFragment_to_bottomSheetFragment)
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
                            findNavController().navigate(R.id.action_profileFragment_to_bottomSheetFragment)
                        }
                    } else {
                        binding.rwEvents.findNavController().navigate(R.id.singInDialog)
                    }
                }

            }
        )
        binding.rwEvents.adapter = adapter
        var id = 0L
        usersViewModel.userId.observe(activity as LifecycleOwner) {
            id = it
        }
//        val id = arguments?.getLong("userId")

        if (id != 0L) {
//            eventViewModel.data.observe(viewLifecycleOwner) { events ->
//                adapter.submitList(events.filter { event -> event.authorId == id })
//            }
            eventViewModel.data.observe(viewLifecycleOwner) {
                //adapter.submitList(it.filter { event -> event.authorId == id })
                val list = it.filter { event ->  event.authorId == id}
                adapter.submitList(list)
                binding.emptyTextFragmentEvents.isVisible = list.isEmpty()
            }
        } else {
            Toast.makeText(context, getString(R.string.id_null), Toast.LENGTH_SHORT).show()
        }

        binding.refresher.setColorSchemeResources(R.color.news)
        binding.refresher.setOnRefreshListener {
            if (id != null && id != 0L) {
                eventViewModel.data.observe(viewLifecycleOwner) {
                    val list = it.filter { event ->  event.authorId == id}
                    adapter.submitList(list)
                    binding.emptyTextFragmentEvents.isVisible = list.isEmpty()
                }
                binding.refresher.isRefreshing = false
            }
        }





            return binding.root
        }
    }