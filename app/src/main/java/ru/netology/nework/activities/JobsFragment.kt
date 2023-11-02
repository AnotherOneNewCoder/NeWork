package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.netology.nework.R
import ru.netology.nework.adapters.JobAdapter
import ru.netology.nework.adapters.OnJobInteractionListener
import ru.netology.nework.databinding.FragmentJobBinding
import ru.netology.nework.dto.Job
import ru.netology.nework.viewmodel.JobsViewModel


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class JobsFragment: Fragment() {
    private val jobViewModel by activityViewModels<JobsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentJobBinding.inflate(inflater, container, false)

        val adapter = JobAdapter(object : OnJobInteractionListener{
            override fun edit(job: Job) {
                jobViewModel.edit(job)
                val bundle = Bundle().apply {
                    putString("job_name", job.name)
                    putString("job_position", job.position)
                    putString("job_started", job.start)
                    job.finish?.let {
                        putString("job_finished", job.finish)
                    }
                    job.link?.let {
                        putString("job_link", job.link)
                    }
                }
                findNavController().navigate(R.id.newJobFragment, bundle)
            }

            override fun delete(job: Job) {
                jobViewModel.removeById(job.id)
            }

        })

        val id = parentFragment?.arguments?.getLong("profileId")

        binding.rwJobs.adapter = adapter



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (id != null) {
                    jobViewModel.setId(id)
                    jobViewModel.loadJobs(id)
                }
                jobViewModel.data.collectLatest {
                    adapter.submitList(it)
                }
            }
        }

//        lifecycleScope.launchWhenCreated {
//            if (id != null) {
//                jobViewModel.setId(id)
//                jobViewModel.loadJobs(id)
//            }
//            jobViewModel.data.collectLatest {
//                adapter.submitList(it)
//            }
//        }








        return binding.root
    }
}