package com.untenty.nauticalknots.data


object GenIDImpl {
    fun getNexID(): Long {
        return System.currentTimeMillis()
    }
}