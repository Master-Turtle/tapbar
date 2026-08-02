package de.turtlemastery.tapbar.system

import com.sun.jna.win32.StdCallLibrary
import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.BaseTSD
import com.sun.jna.ptr.IntByReference
interface ComCtl32 : StdCallLibrary {

    companion object {
        val INSTANCE =
            Native.load(
                "comctl32",
                ComCtl32::class.java
            )
    }

    fun SetWindowSubclass(
        hWnd: HWND,
        proc: SubclassProc,
        uIdSubclass: Pointer?,
        dwRefData: Pointer?
    ): Boolean

    fun DefSubclassProc(
        hWnd: HWND,
        uMsg: Int,
        wParam: Pointer?,
        lParam: Pointer?
    ): Long

    fun DllGetVersion(
        info: DLLVERSIONINFO
    ): Int
}