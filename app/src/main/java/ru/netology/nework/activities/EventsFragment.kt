package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import ru.netology.nework.adapters.EventsAdapter
import ru.netology.nework.adapters.OnEventInteractionListener
import ru.netology.nework.databinding.FragmentEventsBinding
import ru.netology.nework.dto.Event
import ru.netology.nework.viewmodel.AuthViewModel
import ru.netology.nework.viewmodel.EventsViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class EventsFragment: Fragment() {
    private val eventViewModel by activityViewModels<EventsViewModel>()
    private val authViewModel by activityViewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEventsBinding.inflate(
            inflater,
            container,
            false)

        val adapter = EventsAdapter (
            object : OnEventInteractionListener {
                override fun onHideShowFullInfo(event: Event) {
                    // не уверен в необходимости этой функции уже
                }

                override fun onLikeEvent(event: Event) {
                    if(authViewModel.isAuthorized) {
                        if (!event.likedByMe) {
                            eventViewModel.likeById(event.id)
                        } else {
                            eventViewModel.unlikeById(event.id)
                        }
                    } else {
                        // to do dialog
                        Toast.makeText(context, "Logging first!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onParticipateEvent(event: Event) {
                    //eventViewModel...
                }

                override fun onShareEvent(event: Event) {
                    // future sharing
                }

                override fun onShowSpeakersEvent(event: Event) {
                    //eventViewModel...
                }

                override fun onShowCoordsEvent(event: Event) {
                    //navigate to yandex maps
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
                    Toast.makeText(context, "Check internet connection!", Toast.LENGTH_SHORT).show()
                }
            }
            binding.progressBarFragmentEvents.isVisible = it.loading
        }











        return binding.root
    }
}