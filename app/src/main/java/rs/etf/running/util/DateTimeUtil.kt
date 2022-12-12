package rs.etf.running.util

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtil {
    companion object {
        private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        fun formatDate(date: Date): String = simpleDateFormat.format(date)
    }
}