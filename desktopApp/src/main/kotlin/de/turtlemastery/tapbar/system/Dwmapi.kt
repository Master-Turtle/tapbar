package de.turtlemastery.tapbar.system

import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.win32.StdCallLibrary

interface Dwmapi : StdCallLibrary {
    companion object {
        val INSTANCE: Dwmapi = Native.load(
            "dwmapi",
            Dwmapi::class.java
        )

        const val DWMWA_WINDOW_CORNER_PREFERENCE = 33
    }

    fun DwmSetWindowAttribute(
        hwnd: WinDef.HWND?,
        attribute: Int,
        value: IntArray,
        size: Int
    ): Int
}