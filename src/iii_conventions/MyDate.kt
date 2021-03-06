package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
//    override fun compareTo(other: MyDate): Int {
//        return if (year != other.year) year - other.year else if (month != other.month) month - other.month else dayOfMonth - other.dayOfMonth
//    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(other: MyDate): MyDate = MyDate(year + other.year, month + other.month, dayOfMonth + other.dayOfMonth)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

data class MultiInterval(val interval: TimeInterval, val num: Int)

operator fun MyDate.plus(other: TimeInterval): MyDate = addTimeIntervals(other, 1)

operator fun MyDate.plus(other: MultiInterval): MyDate = addTimeIntervals(other.interval, other.num)

operator fun TimeInterval.times(other: Int): MultiInterval = MultiInterval(this, other)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var current: MyDate = this@DateRange.start
        override fun hasNext(): Boolean = current <= this@DateRange.endInclusive
        override fun next(): MyDate {
            val result = current
            current = current.nextDay()
            return result
        }
    }
}
