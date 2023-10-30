package ru.netology.nework.adapters

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nework.R
import ru.netology.nework.databinding.JobCardBinding
import ru.netology.nework.dto.Job
import ru.netology.nework.utils.CommonUtils

class JobViewHolder(
    private val binding: JobCardBinding,
    private val listener: OnJobInteractionListener,
    private val context: Context,
): RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(job: Job) {
        binding.apply {
            jobName.text = job.name
            jobPosition.text = job.position
            jobStarted.text = CommonUtils.convertDate(job.start).substring(0, 10)
            if (job.finish != null) {
                jobFinished.text = CommonUtils.convertDate(job.finish).substring(0, 10)
            } else {
                jobFinished.text = context.getString(R.string.currently_working)
            }
            if (job.link != null) {
                shapkaJobLink.visibility = View.VISIBLE
                jobLink.text = job.link
            } else {
                shapkaJobLink.visibility = View.GONE
                jobLink.visibility = View.GONE
            }
            buttonMenuCardJob.setOnClickListener {
                PopupMenu(context,it).apply {
                    inflate(R.menu.edit_options)
                    menu.setGroupVisible(R.id.options, job.ownedByMe)
                    setOnMenuItemClickListener { item->
                        when(item.itemId) {
                            R.id.edit -> {
                                listener.edit(job)
                                true
                            }
                            R.id.remove -> {
                                listener.delete(job)
                                true
                            }
                            else -> false

                        }
                    }
                }.show()
            }
        }
    }
}