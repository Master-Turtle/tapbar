package de.turtlemastery.tapbar

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform