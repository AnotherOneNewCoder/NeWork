package ru.netology.nework.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

object CommonUtils {
    private val calendar = Calendar.getInstance()
    fun createBitmapFromVector(context: Context, art: Int): Bitmap? {
        val drawble = ContextCompat.getDrawable(context, art) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawble.intrinsicWidth,
            drawble.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        ) ?: return null
        val canvas = Canvas(bitmap)
        drawble.setBounds(0, 0, canvas.width, canvas.height)
        drawble.draw(canvas)
        return bitmap
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatToDate(value: String?): String {
        val transform = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.SHORT)
            .withLocale(Locale.ROOT)
            .withZone(ZoneId.systemDefault())
        return transform.format(Instant.parse(value))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatToInstant(value: String) : String {
        return if (value != " ") {
            val dateTime = SimpleDateFormat(
                "yyyy-MM-dd HH:mm",
                Locale.getDefault()
            )
                .parse(value)
            val transform = DateTimeFormatter.ISO_INSTANT
            transform.format(dateTime?.toInstant())
        } else "2023-01-27T17:00:00.000000Z"
    }

    fun pickDate(editText: EditText?, context: Context?) {
        val datePicker = DatePickerDialog.OnDateSetListener {_, year, monthOfYear, dayOfMonth ->
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = monthOfYear
            calendar[Calendar.DAY_OF_MONTH] = dayOfMonth

            editText?.setText(
                SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
                    .format(calendar.time)
            )
        }
        DatePickerDialog(
            context!!,
            datePicker,
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        ).show()
    }
    fun pickTime(editText: EditText, context: Context) {
        val timePicker = TimePickerDialog.OnTimeSetListener {_, hour, minute ->
            calendar[Calendar.HOUR_OF_DAY] = hour
            calendar[Calendar.MINUTE] = minute

            editText.setText(
                SimpleDateFormat("HH:mm", Locale.ROOT)
                    .format(calendar.time)
            )
        }
        TimePickerDialog(
            context,
            timePicker,
            calendar[Calendar.HOUR_OF_DAY],
            calendar[Calendar.MINUTE],
            true
        ).show()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDate(date: String): String {
        return if (date == "") {
            ""
        } else {
            val parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
            return parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }
    }
    @SuppressLint("NewApi")
    fun selectDateDialog(editText: EditText?, context: Context) {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val pickedDateTime = Calendar.getInstance()
            pickedDateTime.set(year, month, dayOfMonth)
            val result = GregorianCalendar(year, month, dayOfMonth).time
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.uuu'Z'", Locale.getDefault())
            editText?.setText(dateFormat.format(result))
        }, startYear, startMonth, startDay).show()
    }

}