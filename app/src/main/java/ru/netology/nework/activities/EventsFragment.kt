package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import ru.netology.nework.adapters.EventsAdapter
import ru.netology.nework.adapters.OnEventInteractionListener
import ru.netology.nework.databinding.FragmentEventsBinding
import ru.netology.nework.dto.Event
import ru.netology.nework.viewmodel.EventsViewModel

class EventsFragment: Fragment() {
    private val eventViewModel by activityViewModels<EventsViewModel>()
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
                    //eventViewModel...
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
        lifecycleScope.launchWhenCreated {
            eventViewModel.data.collectLatest {
                adapter.submitList(it)
            }
        }






        val unKnow = true




        return binding.root
    }
}