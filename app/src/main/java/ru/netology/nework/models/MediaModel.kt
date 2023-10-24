package ru.netology.nework.models

import android.net.Uri
import ru.netology.nework.dto.TypeAttachment
import java.io.InputStream

data class MediaModel(
    val uri: Uri? = null,
    val inputStream: InputStream? = null,
    val type: TypeAttachment? = null,
)
