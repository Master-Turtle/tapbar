package de.turtlemastery.tapbar.system

import com.sun.jna.Structure
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef

class DLLVERSIONINFO : Structure() {

    @JvmField
    var cbSize: Int = size()

    @JvmField
    var dwMajorVersion: Int = 0

    @JvmField
    var dwMinorVersion: Int = 0

    @JvmField
    var dwBuildNumber: Int = 0

    @JvmField
    var dwPlatformID: Int = 0


    override fun getFieldOrder(): List<String> {
        return listOf(
            "cbSize",
            "dwMajorVersion",
            "dwMinorVersion",
            "dwBuildNumber",
            "dwPlatformID"
        )
    }
}