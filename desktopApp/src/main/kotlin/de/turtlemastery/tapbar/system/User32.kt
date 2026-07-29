package de.turtlemastery.tapbar.system

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef

interface User32 : Library {


    companion object {

        val INSTANCE =
            Native.load(
                "user32",
                User32::class.java
            )
    }


    fun getSystemMetrics(
        index: Int
    ): Int


    fun setWindowPos(
        hWnd: WinDef.HWND?,
        hWndInsertAfter: WinDef.HWND?,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        flags: Int
    ): Boolean
}