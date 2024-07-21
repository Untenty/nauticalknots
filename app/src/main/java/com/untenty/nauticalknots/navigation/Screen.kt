package com.untenty.nauticalknots.navigation

sealed class Screen(val route: String) {
    data object KnotsList : Screen(ROUTE_KNOTS_LIST)
    data object KnotCard : Screen(ROUTE_KNOT)
    data object Settings : Screen(ROUTE_SETTINGS)

    private companion object {
        const val ROUTE_KNOTS_LIST = "KnotsList"
        const val ROUTE_KNOT = "KnotCard"
        const val ROUTE_SETTINGS = "Settings"
    }

}