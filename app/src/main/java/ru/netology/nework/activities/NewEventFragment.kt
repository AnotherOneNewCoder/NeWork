package ru.netology.nework.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.R

import ru.netology.nework.databinding.FragmentNewEventBinding
import ru.netology.nework.dto.Coordinates
import ru.netology.nework.dto.Event
import ru.netology.nework.dto.TypeAttachment
import ru.netology.nework.dto.TypeEvent
import ru.netology.nework.handler.loadImage
import ru.netology.nework.utils.AndroidUtils
import ru.netology.nework.utils.CommonUtils
import ru.netology.nework.utils.StringArg

import ru.netology.nework.viewmodel.EventsViewModel


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NewEventFragment: Fragment() {
    companion object {
        val Bundle.textArg: String? by StringArg
    }



    private val eventsViewModel by activityViewModels<EventsViewModel>()

    private var latitude: Double? = null
    private var longitude: Double? = null
    var type: TypeAttachment? = null

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewEventBinding.inflate(layoutInflater, container, false)
        binding.apply {
            latitude = arguments?.getDouble("map_lat")
            longitude = arguments?.getDouble("map_long")

            arguments?.textArg
                ?.let(editTextContentFragmentNewEvent::setText)
            editTextContentFragmentNewEvent.requestFocus()

            val dateTime = arguments?.getString("edit_event_datetime")?.let {
                CommonUtils.formatToDate(it)
            } //?: CommonUtils.formatToDate("${eventsViewModel.edited.value?.datetime}")
            ?: eventsViewModel.edited.value?.datetime

            val date = dateTime?.substring(0, 10)
            val time = dateTime?.substring(11, 16)

            editTextContentFragmentNewEvent.setText(
                arguments?.getString("edit_event_content") ?: eventsViewModel.edited.value?.content
            )
            if (eventsViewModel.edited.value != Event.emptyEvent) {
                editTextDateFragmentNewEvent.setText(date)
                editTextTimeFragmentNewEvent.setText(time)
            }

            buttonCoordsFragmentNewEvent.setOnClickListener {
                eventsViewModel.changeEventWithContent(
                    editTextContentFragmentNewEvent.text.toString(),
                    CommonUtils.formatToInstant(
                        "${editTextDateFragmentNewEvent.text}" + " " + "${editTextTimeFragmentNewEvent.text}"
                    ),
                    null,
                    editTextLinkFragmentNewEvent.text.toString()
                )

                val bundle = Bundle().apply {
                    putString("action", "new")
                    putString("fragment", "event")
                    if (latitude != null) {
                        Log.d("MyTag", "$latitude")
                        putDouble("mapLat",
                            latitude!!
                        )
                    }
                    if (longitude != null) {
                        Log.d("MyTag", "$longitude")
                        putDouble("mapLong",
                            longitude!!
                        )
                    }

                }
                findNavController().navigate(R.id.mapFragment, bundle)
            }


            editTextDateFragmentNewEvent.setOnClickListener {
                context?.let { item ->
                    CommonUtils.pickDate(editTextDateFragmentNewEvent, item)
                }
            }
            editTextTimeFragmentNewEvent.setOnClickListener {
                context?.let { item ->
                    CommonUtils.pickTime(editTextTimeFragmentNewEvent, item)
                }
            }

            buttonSpeakersFragmentNewEvent.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("action", "speaker")
                }
                findNavController().navigate(R.id.usersFragment, bundle)
            }
            eventsViewModel.edited.observe(viewLifecycleOwner) {
                buttonSpeakersFragmentNewEvent.apply {
                    text = "$text ${eventsViewModel.edited.value?.speakerIds?.count().toString()}"
                }
//

            }


            switcherTypeFormatFragmentNewEvent.setOnCheckedChangeListener { button, checked ->
                if (checked) {
                    eventsViewModel.edited.value = eventsViewModel.edited.value?.copy(type = TypeEvent.OFFLINE)
                } else {
                    eventsViewModel.edited.value = eventsViewModel.edited.value?.copy(type = TypeEvent.ONLINE)
                }

            }
            val photoLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    it.data?.data.let { url ->
                        val stream = url?.let {
                            context?.contentResolver?.openInputStream(it)
                        }
                        eventsViewModel.changeMedia(uri = url, stream, type)
                    }
                }
            buttonTakePhotoFragmentNewEvent.setOnClickListener {
                ImagePicker.Builder(this@NewEventFragment)
                    .cameraOnly()
                    .maxResultSize(2048, 2048)
                    .createIntent(photoLauncher::launch)
                type = TypeAttachment.IMAGE

            }
            buttonChoosePhotoFragmentNewEvent.setOnClickListener {
                ImagePicker.Builder(this@NewEventFragment)
                    .galleryOnly()
                    .galleryMimeTypes(
                        arrayOf(
                            "image/png",
                            "image/jpeg",
                            "image/jpg"
                        )
                    )
                    .maxResultSize(2048, 2048)
                    .createIntent(photoLauncher::launch)
                type = TypeAttachment.IMAGE
            }
            buttonRemovePhotoFragmentNewEvent.setOnClickListener {
                eventsViewModel.changeMedia(null, null, null)
            }

            eventsViewModel.media.observe(viewLifecycleOwner) {
                eventMediaImageView.loadImage(it.uri.toString())
            }








            buttonDoneFragmentNewEvent.setOnClickListener {
                if (editTextContentFragmentNewEvent.text.isNullOrBlank()) {
                    Toast.makeText(
                        context,
                        getString(R.string.content_must_be_filled), Toast.LENGTH_SHORT
                    ).show()
                    editTextContentFragmentNewEvent.error = getString(R.string.fill_me)
                } else {
                    eventsViewModel.changeEventWithContent(
                        content = editTextContentFragmentNewEvent.text.toString(),
                        date = CommonUtils.formatToInstant(
                            "${editTextDateFragmentNewEvent.text}" + " " + "${editTextTimeFragmentNewEvent.text}"
                        ),
                        coordinates = if (latitude == null || longitude ==null) {
                            Coordinates(0.00, 0.00)
                        } else {
                            Coordinates(latitude, longitude)
                        },
                        link = if (editTextLinkFragmentNewEvent.text.isNullOrBlank()){
                            "Not presented!"
                        } else {
                            editTextLinkFragmentNewEvent.text.toString()
                        }
                    )
                    eventsViewModel.saveEvent()
                    AndroidUtils.hideKeyboard(requireView())



                }
            }

        }
        eventsViewModel.eventCreated.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.eventsFragment)
        }
        return binding.root
    }
}