package com.untenty.nauticalknots.data


class GenIDImpl {
    fun getNexID(): Long {
        return System.currentTimeMillis()
    }
}