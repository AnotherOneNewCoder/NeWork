package ru.netology.nework.handler

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ForegroundColorSpan
import android.text.style.LineBackgroundSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class CalendarDecorator(
    private val color: Int,
    dates: Collection<CalendarDay>,
    private val noteName: String,
): DayViewDecorator {
    private val dateSet: HashSet<CalendarDay> = HashSet(dates)
    override fun shouldDecorate(day: CalendarDay): Boolean = dateSet.contains(day)

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(10f, color))
        view.addSpan(ForegroundColorSpan(color))
        view.addSpan(CenterTextSpan(noteName))
    }


}

class CenterTextSpan(private val text: String) : LineBackgroundSpan {

    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lineNumber: Int
    ) {
        val width = paint.measureText(text.subSequence(start, end).toString())
        val x = (left + right) / 2f - width / 2f
        canvas.drawText(text, start, end, x, baseline.toFloat(), paint)
    }
}