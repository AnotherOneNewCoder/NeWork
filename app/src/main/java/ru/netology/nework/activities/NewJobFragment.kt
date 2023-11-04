package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nework.R
import ru.netology.nework.databinding.FragmentNewJobBinding
import ru.netology.nework.utils.AndroidUtils
import ru.netology.nework.utils.CommonUtils
import ru.netology.nework.viewmodel.JobsViewModel


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NewJobFragment : Fragment() {
    private val jobsViewModel by activityViewModels<JobsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewJobBinding.inflate(layoutInflater, container, false)


        val companyName = arguments?.getString("job_name")
        val position = arguments?.getString("job_position")
        val started = arguments?.getString("job_started")
        val finished = arguments?.getString("job_finished")
        val link = arguments?.getString("job_link")

        binding.apply {
            editTextNameFragmentNewJob.setText(companyName)
            editTextPositionFragmentNewJob.setText(position)
            editTextStartedFragmentNewJob.setText(started)
            editTextFinishedFragmentNewJob.setText(
                if (finished == "") " " else finished
            )
            editTextLinkFragmentNewJob.setText(link)



            buttonSaveFragmentNewJob.setOnClickListener {
                AndroidUtils.hideKeyboard(requireView())
                if (editTextNameFragmentNewJob.text.isNullOrBlank() || editTextPositionFragmentNewJob.text.isNullOrBlank() ||
                    editTextStartedFragmentNewJob.text.isNullOrBlank()
                ) {
                    Toast.makeText(
                        context,
                        getString(R.string.all_fields_should_be_filled), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    jobsViewModel.changeJob(
                        companyName = editTextNameFragmentNewJob.text.toString(),
                        position = editTextPositionFragmentNewJob.text.toString(),
                        link = if (editTextLinkFragmentNewJob.text.isNullOrBlank()){
                            "hide"
                        } else {
                            editTextLinkFragmentNewJob.text.toString()
                               },
                        start = editTextStartedFragmentNewJob.text.toString(),
                        finish = if (editTextFinishedFragmentNewJob.text.isNullOrBlank()){
                            "1900-01-01"

                        } else {
                            editTextFinishedFragmentNewJob.text.toString()
                               },
                    )
                    jobsViewModel.save()
//                    jobsViewModel.saveThroughViewModel(
//                        name = editTextNameFragmentNewJob.text.toString(),
//                        position = editTextPositionFragmentNewJob.text.toString(),
//                        started = editTextStartedFragmentNewJob.text.toString(),
//                        finished = editTextFinishedFragmentNewJob.text.toString(),
//                        link = editTextLinkFragmentNewJob.text.toString(),
//                    )
                    AndroidUtils.hideKeyboard(requireView())



                }


            }
            editTextStartedFragmentNewJob.setOnClickListener {
                CommonUtils.selectDateDialog(editTextStartedFragmentNewJob, requireContext())
                val started = editTextStartedFragmentNewJob.text.toString()
                jobsViewModel.startDate(started)

            }
            editTextFinishedFragmentNewJob.setOnClickListener {
                CommonUtils.selectDateDialog(editTextFinishedFragmentNewJob, requireContext())
                val finished = editTextFinishedFragmentNewJob.text.toString()
                jobsViewModel.finishDate(finished)
            }
            editTextStartedFragmentNewJob.setOnClickListener {
                context?.let { item ->
                    CommonUtils.pickDate(editTextStartedFragmentNewJob, item)
                }
            }
            editTextFinishedFragmentNewJob.setOnClickListener {
                context?.let { item ->
                    CommonUtils.pickDate(editTextFinishedFragmentNewJob, item)
                }
            }
            jobsViewModel.created.observe(viewLifecycleOwner) {
                Toast.makeText(context, R.string.saved, Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
        return binding.root
    }
}