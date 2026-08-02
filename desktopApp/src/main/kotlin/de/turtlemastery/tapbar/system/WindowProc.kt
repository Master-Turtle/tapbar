package de.turtlemastery.tapbar.system

import com.sun.jna.Callback
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef.HWND

interface WindowProc : Callback {

    fun callback(
        hwnd: HWND,
        uMsg: Int,
        wParam: Pointer?,
        lParam: Pointer?
    ): Long
}