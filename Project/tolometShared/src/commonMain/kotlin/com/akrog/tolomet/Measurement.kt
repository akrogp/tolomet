package com.akrog.tolomet

class Measurement(
    private val validMinimum: Float? = null,
    private val validMaximum: Float? = null
) {
    private val map = mutableMapOf<Long, Number>()
    private var times: Array<Long>? = null
    private var values: Array<Number>? = null
    private var cachedMinimum: Number? = null
    private var cachedMaximum: Number? = null

    private fun clearCache() {
        times = null
        values = null
        cachedMinimum = null
        cachedMaximum = null
    }

    fun put(time: Long, value: Number?) {
        if (value == null) return
        if (validMinimum != null && value.toFloat() < validMinimum) return
        if (validMaximum != null && value.toFloat() > validMaximum) return

        // Note: Simple rounding to the minute as in the original Java code
        val roundedTime = (time / 60000) * 60000

        map[roundedTime] = value
        clearCache()
    }

    fun getEntrySet(): Set<Map.Entry<Long, Number>> = map.entries

    fun getTimes(): Array<Long> {
        if (times == null) {
            times = map.keys.sorted().toTypedArray()
        }
        return times!!
    }

    fun getValues(): Array<Number> {
        if (values == null) {
            val sortedKeys = getTimes()
            values = sortedKeys.map { map[it]!! }.toTypedArray()
        }
        return values!!
    }

    fun isEmpty(): Boolean = map.isEmpty()

    fun getStamp(): Long? {
        if (isEmpty()) return null
        val times = getTimes()
        return times.last()
    }

    fun getBegin(): Long? {
        if (isEmpty()) return null
        val times = getTimes()
        return times.first()
    }

    fun get(time: Long): Number? = map[time]

    fun clear() {
        map.clear()
        clearCache()
    }

    fun clear(fromStamp: Long) {
        val toRemove = map.keys.filter { it >= fromStamp }
        toRemove.forEach { map.remove(it) }
        clearCache()
    }

    fun merge(other: Measurement) {
        map.putAll(other.map)
        clearCache()
    }
}
