package de.turtlemastery.tapbar

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef.HWND


interface User32 : Library {


    companion object {

        val INSTANCE =
            Native.load(
                "user32",
                User32::class.java
            )
    }


    fun GetSystemMetrics(
        index: Int
    ): Int


    fun SetWindowPos(
        hWnd: HWND?,
        hWndInsertAfter: HWND?,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        flags: Int
    ): Boolean
}