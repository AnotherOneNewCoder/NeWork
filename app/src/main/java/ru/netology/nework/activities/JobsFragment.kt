package ru.netology.nework.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import ru.netology.nework.adapters.JobAdapter
import ru.netology.nework.adapters.OnJobInteractionListener
import ru.netology.nework.databinding.FragmentJobBinding
import ru.netology.nework.dto.Job
import ru.netology.nework.viewmodel.JobsViewModel

class JobsFragment: Fragment() {
    private val viewModel by activityViewModels<JobsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentJobBinding.inflate(inflater, container, false)

        val adapter = JobAdapter(object : OnJobInteractionListener{
            override fun edit(job: Job) {

            }

            override fun delete(job: Job) {
                viewModel.delete(job.id)
            }

        })

        binding.rwJobs.adapter = adapter

        lifecycleScope.launchWhenCreated {
            viewModel.data.collectLatest {
                adapter.submitList(it)
            }
        }








        return binding.root
    }
}