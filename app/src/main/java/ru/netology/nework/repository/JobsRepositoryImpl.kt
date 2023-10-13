package ru.netology.nework.repository


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.Job

class JobsRepositoryImpl: JobsRepository {

    var jobs = listOf(
        Job(
            id = 1L,
            name = "Mcdonalds",
            position = "Cleaner",
            start = "01.10.1999",
            finish = "02.10.1999",
            link = "mcdonalds.com",
        ),
        Job(
            id = 2L,
            name = "BurgerKing",
            position = "Cook",
            start = "02.10.1999",
            finish = null,
            link = null,
        ),        Job(
            id = 1L,
            name = "Mcdonalds",
            position = "Cleaner",
            start = "01.10.1999",
            finish = "02.10.1999",
            link = "mcdonalds.com",
        ),
        Job(
            id = 2L,
            name = "BurgerKing",
            position = "Cook",
            start = "02.10.1999",
            finish = null,
            link = null,
        ),
        Job(
            id = 1L,
            name = "Mcdonalds",
            position = "Cleaner",
            start = "01.10.1999",
            finish = "02.10.1999",
            link = "mcdonalds.com",
        ),
        Job(
            id = 2L,
            name = "BurgerKing",
            position = "Cook",
            start = "02.10.1999",
            finish = null,
            link = null,
        ),
        Job(
            id = 1L,
            name = "Mcdonalds",
            position = "Cleaner",
            start = "01.10.1999",
            finish = "02.10.1999",
            link = "mcdonalds.com",
        ),
        Job(
            id = 2L,
            name = "BurgerKing",
            position = "Cook",
            start = "02.10.1999",
            finish = null,
            link = null,
        ),        Job(
            id = 1L,
            name = "Mcdonalds",
            position = "Cleaner",
            start = "01.10.1999",
            finish = "02.10.1999",
            link = "mcdonalds.com",
        ),
        Job(
            id = 2L,
            name = "BurgerKing",
            position = "Cook",
            start = "02.10.1999",
            finish = null,
            link = null,
        ),
        Job(
            id = 1L,
            name = "Mcdonalds",
            position = "Cleaner",
            start = "01.10.1999",
            finish = "02.10.1999",
            link = "mcdonalds.com",
        ),
        Job(
            id = 2L,
            name = "BurgerKing",
            position = "Cook",
            start = "02.10.1999",
            finish = null,
            link = null,
        ),

    )
    val data = MutableLiveData(jobs)


    override fun getAll(): Flow<List<Job>> = data.asFlow()

    override fun removeById(id: Long) {
        data.value?.filter { it.id != id }
    }

    override fun editJob(job: Job) {

    }
}