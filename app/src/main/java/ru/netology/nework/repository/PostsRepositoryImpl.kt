package ru.netology.nework.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.Attachment
import ru.netology.nework.dto.Coordinates
import ru.netology.nework.dto.Post
import ru.netology.nework.dto.TypeAttachment

class PostsRepositoryImpl: PostsRepository {

    val setList = setOf(
        11L,
        12L,
        46L,
    )

    val posts = listOf(
        Post(
            id = 1L,
            authorId = 11L,
            authorAvatar = null,
            author = "Vasya Petrov",
            authorJob = null,
            content = "Два руководителя армии Польши - начальник Генерального штаба Раймунд Анджейчак и оперативный командующий Вооруженными силами Томаш Пиотровский - уволились после разгоревшегося скандала с обнаружением обломков ракеты. Об этом сообщает телеканал Polsat.\n" +
                    "\n" +
                    "Отмечается, что официально причины подачи заявлений об отставке сразу двумя генералами не называются.\n" +
                    "\n" +
                    "\"Каждый военнослужащий имеет право подать такое заявление, генерал этим воспользовался\", - заявили в Генштабе армии Польши, комментируя увольнение Пиотровского.\n" +
                    "\n" +
                    "Вещатель отмечает, что оба командующих уволились после того, как стали центральными фигурами скандала, разгоревшегося в апреле 2023 года после обнаружения в пригороде города Быдгощ обломков ракеты неизвестного происхождения.\n" +
                    "\n" +
                    "После этого министр национальной обороны Польши Мариуш Блащак обвинил Пиотровского, который якобы знал о падении ракеты, но умолчал об этом, в халатности и предложил его уволить. Однако дальше предложения дело не пошло. Анджейчак же поддержал генерала.\n" +
                    "\n" +
                    "При этом польская оппозиция уже называет увольнение генералов ударом для самого Блащака и требует его отставки.",
            published = "10.10.2023 16:30",
            coords = null,
            link = "https://ren.tv/news/v-mire/1150440-v-komandovanii-armii-polshi-proshli-uvolneniia-iz-za-skandala-s-raketoi?utm_source=yxnews&utm_medium=desktop",
            likeOwnersId = setList,
            mentionId = setList,
            likedByMe = false,
            mentionedMe = true,
            attachment = null,
            ownedByMe = true


        ),
        Post(
            id = 2L,
            authorId = 12L,
            authorAvatar = "https://ya.ru/images/search?utm_source=main_stripe_big&text=%D0%A1%D0%BE%D0%B2%D0%BE%D0%BE%D0%B1%D1%80%D0%B0%D0%B7%D0%BD%D1%8B%D0%B5&nl=1&source=morda",
            author = "Image Test",
            authorJob = "Yandex pic",
            content = "Test imageview",
            published = "10.10.23 17:00",
            coords = Coordinates("55.758134", "37.952100"),
            link = null,
            likeOwnersId = emptySet(),
            mentionId = emptySet(),
            likedByMe = false,
            mentionedMe = false,
            attachment = Attachment("https://ya.ru/images/search?utm_source=main_stripe_big&text=%D0%97%D0%B0%D0%B2%D1%82%D1%80%D0%B0%D0%BA&nl=1&source=morda",
                TypeAttachment.IMAGE),
            ownedByMe = false
        ),
        Post(
            id = 2L,
            authorId = 12L,
            authorAvatar = "https://ya.ru/images/search?utm_source=main_stripe_big&text=%D0%A1%D0%BE%D0%B2%D0%BE%D0%BE%D0%B1%D1%80%D0%B0%D0%B7%D0%BD%D1%8B%D0%B5&nl=1&source=morda",
            author = "Audio Test",
            authorJob = "Yandex pic",
            content = "Audio Test",
            published = "10.10.23 17:00",
            coords = Coordinates("55.758134", "37.952100"),
            link = null,
            likeOwnersId = emptySet(),
            mentionId = emptySet(),
            likedByMe = false,
            mentionedMe = false,
            attachment = Attachment("https://ya.ru/images/search?utm_source=main_stripe_big&text=%D0%97%D0%B0%D0%B2%D1%82%D1%80%D0%B0%D0%BA&nl=1&source=morda",
                TypeAttachment.AUDIO),
            ownedByMe = false
        ),
        Post(
            id = 2L,
            authorId = 12L,
            authorAvatar = "https://ya.ru/images/search?utm_source=main_stripe_big&text=%D0%A1%D0%BE%D0%B2%D0%BE%D0%BE%D0%B1%D1%80%D0%B0%D0%B7%D0%BD%D1%8B%D0%B5&nl=1&source=morda",
            author = "Video Test",
            authorJob = "Yandex pic",
            content = "Video Test",
            published = "10.10.23 17:00",
            coords = Coordinates("55.758134", "37.952100"),
            link = null,
            likeOwnersId = emptySet(),
            mentionId = emptySet(),
            likedByMe = false,
            mentionedMe = false,
            attachment = Attachment("https://ya.ru/images/search?utm_source=main_stripe_big&text=%D0%97%D0%B0%D0%B2%D1%82%D1%80%D0%B0%D0%BA&nl=1&source=morda",
                TypeAttachment.VIDEO),
            ownedByMe = false
        ),)

    val data = MutableLiveData(posts)
    override fun getAll(): Flow<List<Post>> = data.asFlow()
}