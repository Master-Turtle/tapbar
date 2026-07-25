package de.turtlemastery.tapbar

import com.sun.jna.Structure
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.WinDef.RECT


const val ABM_NEW = 0x00000000
const val ABM_REMOVE = 0x00000001
const val ABM_QUERYPOS = 0x00000002
const val ABM_SETPOS = 0x00000003

const val ABE_LEFT = 0
const val ABE_TOP = 1
const val ABE_RIGHT = 2
const val ABE_BOTTOM = 3


class TapBarDATA : Structure() {

    @JvmField
    var cbSize: Int = size()

    @JvmField
    var hWnd: HWND? = null

    @JvmField
    var uCallbackMessage: Int = 0

    @JvmField
    var uEdge: Int = 0

    @JvmField
    var rc: RECT = RECT()

    @JvmField
    var lParam: Long = 0


    override fun getFieldOrder(): List<String> {
        return listOf(
            "cbSize",
            "hWnd",
            "uCallbackMessage",
            "uEdge",
            "rc",
            "lParam"
        )
    }
}
