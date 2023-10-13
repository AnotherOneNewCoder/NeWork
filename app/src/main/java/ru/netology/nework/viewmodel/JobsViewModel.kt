package ru.netology.nework.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nework.dto.Job
import ru.netology.nework.repository.JobsRepository
import ru.netology.nework.repository.JobsRepositoryImpl


class JobsViewModel: ViewModel() {
    private val repository: JobsRepository = JobsRepositoryImpl()
    val data = repository.getAll()
    fun delete(id: Long) {
        repository.removeById(id)
    }
}