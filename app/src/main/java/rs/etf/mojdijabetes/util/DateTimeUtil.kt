package rs.etf.mojdijabetes.util

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtil {
    companion object {
        private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        fun formatDate(date: Date): String = simpleDateFormat.format(date)

        fun realMinutesToString(realMinutes: Double): String {
            val minutes = realMinutes.toInt()
            val seconds = (60 * (realMinutes - minutes)).toInt()
            return "${minutes}:${String.format("%02d", seconds)}"
        }
    }
}