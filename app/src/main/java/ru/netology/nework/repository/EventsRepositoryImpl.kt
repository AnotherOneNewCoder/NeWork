package ru.netology.nework.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.Attachment
import ru.netology.nework.dto.Coordinates
import ru.netology.nework.dto.Event
import ru.netology.nework.dto.TypeAttachment
import ru.netology.nework.dto.TypeEvent

class EventsRepositoryImpl: EventsRepository {

    val speakers = setOf(11L, 12L, 13L)

    var events = listOf( Event(
        id = 1L,
        authorId = 1L,
        authorAvatar = null,
        author = "Петров Пётр Иванович",
        //authorAvatar = "https://yandex.ru/images/search?text=negjt+kbwj&pos=4&rpt=simage&img_url=https%3A%2F%2Fkartinkof.club%2Fuploads%2Fposts%2F2022-04%2F1649845723_1-kartinkof-club-p-rzhachnie-kartinki-glaza-1.jpg&from=tabbar&lr=213",
        authorJob = "ООО Диспансер №3",
        content = "В мерседес врезается запорожец. Из мерса вылазит братки, в запоре сидит дедок и говорит:\n" +
                "— Вы бы меня не трогали, я колдун!\n" +
                "— Пошел ты колдун, ты нам бампер помял и царапин наставил, с тебя 1000\$\n" +
                "Старик достает штуку \$, кидает им со словами:\n" +
                "— Нате, обосритесь.\n" +
                "Братки день срут, второй срут, третий срут, четвертый, пятый и т.д., на седьмой отыскивают старика, дают ему 1000\$ и сверху ещё 5000\$:\n" +
                "— Мужик, сделай чтобы не срали.\n" +
                "Братки день не срут, второй не срут, третий не срут, четвертый, пятый и т.д., на седьмой опять к старику:\n" +
                "— Что надо, чтоб всё было Ок? говорят братки;\n" +
                "— Давайте всё, что у вас есть.\n" +
                "Привозят они ему все деньги, вырученные за квартиры, машины и т.п. Отдают:\n" +
                "— Точно все будет нормально?\n" +
                "— Да точно, братки, все будет нормально, не ссыте!\n" +
                "\n" +
                "https://anekdoty.ru/",
        published = "07.10.2023 15:27",
        datetime = "10.11.2023",
        coords = Coordinates("55.746440", "38.009043"),
        type = TypeEvent.OFFLINE,
        speakersIds = speakers,
        link = "https://kudamoscow.ru/event/auto-tuning-show-2023/",
        likedByMe = true,
        likeOwnerIds = speakers,
        participantsIds = speakers,
        participatedByMe = true,
        ownedByMe = false,
        attachment = null,

    ),
     Event(
        id = 2L,
        authorId = 3L,
        author = "Иванов Иван Петрович",
        authorAvatar = "https://yandex-images.clstorage.net/5n1Vlv214/427e6btRet0/6URu2-uHEMsKd-K6ViVQYJitKzlaQt8HQieQgW576j4pktDhgBYkPwYymWnQ0JuddJDreVfJoqjiFM4i6MP-5D1Bvcs7QoZcGjCw6VfnEWdKank5S4XLhYfhRtb_bGjofFJeel2fOeQM6kV0j5pI3bZRvv8EmzPmLQSD5_2Vrv1iiGIVpF_aCM3ob_wZ5tomySWuPgHBQgVOfYTeujio_mDIFb3elXCtwyUNMMEYiBD-WH4zX1T1L6KczaF8k6Hw6Y2uW6zfEAQGb2g7Gy_bbwAiYfTLgcLPBrRFkD82ar3khxT_k9KyogbizX8JFAUUeFg0fdaVPSGp1QBkd5-hfWMLbF3lkR6MiiQm_thll2TOpKCySozJT0p6S9yz-mkyLMPa8dDSuG_GZ843D9wGW7dV83ddG_elaZiPJvAWqDinSG-R5hebTAWi5_wQoB0qxqgivctJhcuDugMZMjSn-uIOEXZdmv0jRqYKvsvbglQ0XrL_Xlt_62CQBeD1nq815U_kluPVUcwOZyH-HKwbI4trLnBIAQZLhrgHEL9x4n1tTBR4WJa0oM5jTrwDWkwQ_Vl8upyUMuzpVYQoNdareaJF6tbnlZZBhmIvMt0o0y3Co29yT8gEyY92x9c_9Kf7pQvbd5nV8ibP78T9zxEPnHcZc3UZmfdi79DMq3eb6nhhh2vV75Tfxk5n4nyVYlrsgarvsgLCxUtKesZeMr_gf-EOGzUZ0npoQmyMPoiSz5T81LI9Wpw-au4Ryug6mmbwJUUu16JW1suA5yk-GeBRqwhuZH2MioLNxbZCWXFy77Ztz5K4EZL4r4KkgncEm4tZ95VxuZ9XM-PuEIDut5sgM2XLpRoiEhmIDuQuN53mW-UHKmc8isaAgEeyTh0zuWo-ZYdaNNcWc2OKIoE7TZmL1byZdHSf0rtr6BbP5DYXIPpnQ2rR5t_VgQMorfqeaNDjyCSivc-JgIdGPE7T9U",
        authorJob = "ООО Диспансер №6",
        content = "Пошли три иудея православие принимать. Пришли к батюшке, посоветовались. Батюшка им:\n" +
                "— Ну, ладно. Только имена вам на православные надо поменять. Как тебя зовут? — первого спрашивает.\n" +
                "— Гиршем, батюшка.\n" +
                "— Ну, будешь Гришкой, что ли. И одно звучно, и однозначно. А тебя как? — второго спрашивает.\n" +
                "— Мойша я.\n" +
                "— Будешь у нас Мишей. И одно звучно, и однозначно. Ну, а ты что? — у последнего.\n" +
                "Тот чего—то покраснел, не отвечает.\n" +
                "— Не бойса, не бойса. Говори, как есть.\n" +
                "— Сруль меня, батюшка, зовут, есть имя такое еврейское.\n" +
                "Почесал поп затылок, подумал, и выдает:\n" +
                "— Будешь, значить. Акакием. Ну, не одно звучно, но однозначно!\n" +
                "\n" +
                "https://anekdoty.ru/",
        published = "07.10.2023 16:45",
        datetime = "21.12.2023",
        coords = null,
        type = TypeEvent.ONLINE,
        speakersIds = speakers,
        link = null,
        likedByMe = false,
        likeOwnerIds = speakers,
        participantsIds = speakers,
        participatedByMe = true,
        ownedByMe = false,
        attachment = Attachment("https://yandex.ru/images/search?p=1&text=%D0%BC%D0%B5%D1%80%D0%BE%D0%BF%D1%80%D0%B8%D1%8F%D1%82%D0%B8%D1%8F+%D0%B2+%D0%BC%D0%BE%D1%81%D0%BA%D0%B2%D0%B5&pos=0&rpt=simage&img_url=https%3A%2F%2Fres.cloudinary.com%2Fdeokysfpy%2Fimage%2Fupload%2Fk7iqoszfxmzvoujl6rqs&from=tabbar&lr=213",
            TypeAttachment.IMAGE)

    ))
    private val data = MutableLiveData(events)

    override fun getAll(): Flow<List<Event>> = data.asFlow()
}