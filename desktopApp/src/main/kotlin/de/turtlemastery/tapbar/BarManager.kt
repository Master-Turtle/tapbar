package de.turtlemastery.tapbar

import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.WinDef.RECT
import com.sun.jna.platform.win32.User32


object BarManager {

    val data = TapBarDATA()
    var WIDTH = 70

    fun register(hwnd: HWND) {
        data.hWnd = hwnd

        Shell32.INSTANCE.SHAppBarMessage(
            ABM_NEW,
            data
        )
        applyBar()
    }

    fun applyBar() {
        val height =
            User32.INSTANCE.GetSystemMetrics(1)

        val rect = RECT()

        rect.left = 0
        rect.top = 0
        rect.right = WIDTH
        rect.bottom = height


        data.uEdge = ABE_LEFT
        data.rc = rect

        // Windows fragt den Bereich ab
        Shell32.INSTANCE.SHAppBarMessage(
            ABM_QUERYPOS,
            data
        )
        // Position wirklich setzen
        Shell32.INSTANCE.SHAppBarMessage(
            ABM_SETPOS,
            data
        )

        setWindowPos(data.rc)
    }

    fun releaseSpace(){
        Shell32.INSTANCE.SHAppBarMessage(
            ABM_REMOVE,
            data
        )
    }

    fun setWindowPos(rect: RECT){
        User32.INSTANCE.SetWindowPos(
            data.hWnd,
            null,
            data.rc.left,
            data.rc.top,
            data.rc.right - data.rc.left,
            data.rc.bottom - data.rc.top,
            0x0010 or 0x0004 or 0x0020
        )
    }

    fun changeWidth(newWidth: Int){
        WIDTH = newWidth
        applyBar()
    }

}