package de.turtlemastery.tapbar.system

import com.sun.jna.win32.StdCallLibrary.StdCallCallback
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.BaseTSD
interface SubclassProc : StdCallCallback {

    fun callback(
        hWnd: HWND,
        uMsg: Int,
        wParam: Pointer?,
        lParam: Pointer?,
        uIdSubclass: BaseTSD.ULONG_PTR,
        dwRefData: BaseTSD.ULONG_PTR
    ): Long
}